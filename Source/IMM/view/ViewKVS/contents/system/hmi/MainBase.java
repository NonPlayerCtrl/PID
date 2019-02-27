package system.hmi;

import java.awt.Dimension;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import Dialogs.hmi.KeySelectorDialog;
import ServiceNet.hmi.service.KPServiceNetService;
import com.keba.kemro.kv.control.KvFrame;
import com.keba.kemro.kv.control.KvMask;
import com.keba.kemro.kv.help.KvHelpBroker;
import com.keba.kemro.kv.util.KvToolkit;
import com.keba.kemro.kvs.control.KvsApplication;
import com.keba.kemro.kvs.control.KvsUserManager;
import com.keba.kemro.kvs.keplast.control.user.KUserLogin;
import com.keba.kemro.kvs.keplast.control.util.AlarmMaskFooler;
import com.keba.kemro.kvs.keplast.control.util.KPOperationModeChangedILogWriter;
import com.keba.kemro.kvs.keplast.general.util.VersionInfoReader;
import com.keba.kemro.kvs.keplast.util.InjectGraphSupport;
import com.keba.kemro.kvs.keplast.util.PDPAutoStart;
import com.keba.kemro.kvs.widgets.KvsMaskChangeButton;
import com.keba.kemro.plc.event.UserChangedEvent;
import com.keba.kemro.plc.network.AbstractUser;
import com.keba.kemro.plc.network.DefaultUser;
import com.keba.kemro.plc.service.HmiVariableService;
import com.keba.kemro.plc.service.HmiAlarmService;
import com.keba.kemro.plc.variable.KVariable;
import com.keba.keplast.general.util.KPVisibilityChangedHandler;
import com.keba.keplast.simulation.Simulation;
import com.keba.keplast.utils.PanelUtils;
import com.keba.keplast.widgets.menu.IPopupMask;
import com.keba.keplast.widgets.menu.MainMenuBar;
import com.keba.keplast.plc.variable.KPSysVarTextParser;
import com.keba.keplast.plc.alarm.KPAlarmTextFactory;
import com.keba.util.Config;
import com.keba.util.UserManager;
import composite.MachineLockDialogPanel.MachineLockDialogPanel;

public abstract class MainBase extends Main_generated {

   private static final long serialVersionUID = 1L;

   protected MainMenuBar mainMenu = MainMenuBar.getInstance();

   private AbstractUser kvsDefaultUser;
   private AbstractUser kePlastDefaultUser;

   protected boolean initDone = false;

   private String mTextIdWarningLabel;

   static {
      // set text-parser for short/long text formatting
      KVariable.setTextParser(new KPSysVarTextParser());
   }

   public MainBase() throws Exception {
      super();
      try {
         initializeWindowTitle();
         // waits for initialization done in simulation mode, otherwise the heating and
         // movement icons does not work right.
         Simulation.getInstance().waitForInitializationOnSimulation();
         initialize();

         // add mouse listener for panel overlay (quick username/password input)
         glassPanelUserData.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
               UserManager.getSharedInstance().showUserDialog();
            }
         });

         KeySelectorDialog keySelectorDialog = new KeySelectorDialog();
         keySelectorDialog.initialize();
      } catch (Exception ex) {
         CAT.error("Constructor: ", ex);
      }
   }

   public void Init_Generated() throws Exception {
      // set text-parser for alarm text formatting
      HmiAlarmService.getService().setAlarmTextFactory(new KPAlarmTextFactory());

      if (MachineEquipment.isTestVersion())
         mTextIdWarningLabel = "TestVersionLabel";

      if (MachineEquipment.isApplPatched())
         mTextIdWarningLabel = "PatchedAppl";

      super.Init_Generated();
      changeLocale(Locale.getDefault());
   }

   void initialize() throws Exception {

      setBackButton(backButton);

      mainMenu.setMainButtons(new KvsMaskChangeButton[] { maskButtonOverview, maskButtonMold, maskButtonEjector, maskButtonCore,
            maskButtonInjection, maskButtonPlasticize, maskButtonNozzle, maskButtonHeating, maskButtonOperation, maskButtonAlarm });
      mainMenu.setContextButtonsPanel(contextMenu1);
      MainMenuCustomizer.customizeMenu(mainMenu);

      PDPAutoStart.getInstance().start();
      // initialize inject graph in IEC
      InjectGraphSupport.initializeInjectGraph();
      // load simulation panel
      Simulation.getInstance().loadSimulation();
      // systemvariable to write userlevel
      KUserLogin.setUserLevelVariable("system.sv_UserLevel");
      // enable auto login
      KUserLogin.activateKeySwitch();

      // enable operation mode change to info log
      KPOperationModeChangedILogWriter.start();
      // try to find alarm-help
      try {
         String mapFile = Config.getStringProperty("alarmmap");
         KvHelpBroker.getInstance().addMapfile(mapFile);
      } catch (MissingResourceException e) {
         CAT.warn("alarmmap entry not found in hmiCfg");
      }
      // activate alarmmaskfooler
      AlarmMaskFooler.activate();
      // activate visbility changed handler
      KPVisibilityChangedHandler.getInstance().startRefresh();
      // activate ServiceNet-Service (to grant remote-access)
      KPServiceNetService.getInstance().startService();

      mainMenu.updateContextPane(maskButtonOverview);

      // Get KVS default user and KePlast default user
      kvsDefaultUser = KvsUserManager.getSharedInstance().getUserAttribs(DefaultUser.NAME);
      kePlastDefaultUser = KvsUserManager.getSharedInstance().getUserAttribs("Worker");

      new StartupInfo().showIfAvailable();
      new StartupAutoLoad().autoLoad();
   }

   protected void initializeWindowTitle() {
      /***************************************************************************
       * set the Version to the title on win NT and also set the window to not resizeable
       **************************************************************************/
      if (!KvToolkit.isVxWorks()) {
         // since CR_0031308
         // check for external set application title, if not available use old
         // version
         String sTitle = null;
         try {
            sTitle = ResourceBundle.getBundle("applinfo").getString("versioninfo");
         } catch (Exception ex) {
            // application title not found, use old version
         }
         if (sTitle == null) {
            try {
               String sVersion = VersionInfoReader.getInstance().getVersionInfoKePlastHMI();
               String sHostIP;
               if (sVersion != null) {

                  sTitle = sVersion;
                  // add host IP
                  sHostIP = HmiVariableService.getService().getHostName();
                  if (!sHostIP.equals("")) {
                     sTitle = sTitle + " (" + sHostIP + ")";
                  }
                  sTitle = sTitle + " [";
                  // read panel name
                  String panelName = PanelUtils.getPanelName();
                  if (panelName != null) {
                     sTitle = sTitle + panelName + ", ";
                  }
                  // set screen size to title
                  Dimension scrSize = KvToolkit.getScreenSize();
                  sTitle = sTitle + scrSize.width + "x" + scrSize.height + "]";
                  KvsApplication.getInstance().setTitle(sTitle);
               }
            } catch (Exception ex) {
               CAT.error("initializeWindowTitle: ", ex);
            }
         }
      }
   }

   /*
    * (non-Javadoc) show mask from KvFrame. Updates the context menu pane.
    * 
    * @see com.keba.kemro.kv.control.KvFrame#showMask(com.keba.kemro.kv.control.KvMask)
    */
   public void showMask(KvMask mask) {
      if (!initDone) {
         initDone = true;
         onShowInitMask();
      }
      if (!showPopupMask(mask)) {
         showMaskFrame(mask);
      }
      if (mainMenu != null) {
         mainMenu.updateContextPane(mask.getID());
      }
   }

   /**
    * invoked before the first mask is shown
    */
   protected void onShowInitMask() {
      MachineLockDialogPanel.register();
   }

   /**
    * Calls the {@link KvFrame#showMask(KvMask)} from base {@link KvFrame}.
    * 
    * @param mask
    */
   protected final void showMaskFrame(KvMask mask) {
      super.showMask(mask);
   }

   /**
    * Checks for popup mask and shows it when available.
    * 
    * @param mask
    * @return
    */
   protected boolean showPopupMask(KvMask mask) {
      KvMask popupMask = null;
      if (mask instanceof IPopupMask) {
         popupMask = ((IPopupMask) mask).getMainMask();
         if (popupMask != null) {
            super.showMask(popupMask);
         }
         return true;
      }
      return false;
   }

   public void userChanged(UserChangedEvent e) {
      if (kvsDefaultUser != null && kePlastDefaultUser != null && e.getUser().equals(kvsDefaultUser)) {
         // Use KePlast default user ("Worker") as default user
         e = new UserChangedEvent(kePlastDefaultUser);
      }

      super.userChanged(e);
   }

   protected void changeLocale(Locale locale) {
      super.changeLocale(locale);
      try {
         textBundle = ResourceBundle.getBundle("system.hmi.text.systemtx",
               locale);

         if (lblTestversion != null) {
            lblTestversion.setLabel(textBundle.getString(mTextIdWarningLabel));
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }
}
