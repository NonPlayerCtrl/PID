package ServiceNet.hmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import com.ibm.log4j.Category;
import com.keba.kemro.kv.dialog.KvDialogInfoInterface;
import com.keba.kemro.kv.dialog.KvMessageDialog;
import com.keba.kemro.kv.dialog.KvProgressMonitor;
import com.keba.kemro.plc.event.ValueChangedEvent;
import com.keba.kemro.plc.event.ValueChangedListener;
import com.keba.kemro.plc.service.HmiVariableService;
import com.keba.kemro.plc.variable.KVariable;

public class ServiceNetSimInfoMask extends ServiceNetSimInfoMask_generated implements ValueChangedListener {
   private static final long serialVersionUID = 1L;
   private final String VAR_PATH_START_DOWNLOAD = "ServiceNet.sv_bDownloadSIMInfo";
   private final String VAR_PATH_ERROR_CODE = "ServiceNet.sv_iDownloadSIMInfoErrorCode";
   private String RESOURCE_BUNDLE_DLG = "ServiceNet.hmi.text.message";
   private ResourceBundle m_bundleDlg;
   private KVariable m_varStartDownload;
   private KVariable m_varErrorCode;
   private KvProgressMonitor mon;
   private Category CAT;

   public ServiceNetSimInfoMask() throws Exception {
      super();
      CAT = Category.getInstance(this.getClass().getName());
      try {
         initComponents();
         initVariables();
         m_bundleDlg = ResourceBundle.getBundle(RESOURCE_BUNDLE_DLG);
      }
      catch (Exception ex) {
         CAT.error("Constructor: ", ex);
      }
   }

   private void initComponents() {
      btnDownloadToModem.removeActionListener(this);
      btnDownloadToModem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            btnDownloadToModemClicked();
         }
      });
      btnSimList.setMask("ServiceNet.hmi.ServiceNetSimListMask");
   }

   private void initVariables() {
      try {
         m_varStartDownload = HmiVariableService.registerVariable(VAR_PATH_START_DOWNLOAD, this);
         m_varErrorCode = HmiVariableService.getService().getVariable(VAR_PATH_ERROR_CODE);
      }
      catch (Exception ex) {
         CAT.error("initVariables: ", ex);
      }
   }

   private void btnDownloadToModemClicked() {
      DlgInfoInterface info;
      String title, message;

      info = new DlgInfoInterface();
      try {
         title = m_bundleDlg.getString("download_dlg_title");
         message = m_bundleDlg.getString("download_dlg_message");
      }
      catch (Exception ex) {
         CAT.error("btnDownloadToModemClicked: ", ex);
         title = "Warning";
         message = "Attention!; ;The correct SIM card information must be downloaded to the modem before a ;SIM card is inserted into the modem! After download has finished successfully, ;unplug the modem from the supply voltage, insert the SIM card and afterwards ;restart the modem.";
      }
      KvMessageDialog.showModal(message, KvMessageDialog.WARNING_MESSAGE, KvMessageDialog.OK_CANCEL, title, true, info);
   }

   private class DlgInfoInterface implements KvDialogInfoInterface {

      public boolean getPressedOption(int option) {
         String title, message;
         
         if (option == KvDialogInfoInterface.OK) {
            try {
               title = m_bundleDlg.getString("downloading_title");
               message = m_bundleDlg.getString("downloading_message");
            }
            catch (Exception ex) {
               CAT.error("btnDownloadToModemClicked: ", ex);
               title = "Downloading to modem ...";
               message = "This may take some minutes.";
            }
            try {
               HmiVariableService.getService().writeValue(m_varStartDownload, Boolean.TRUE);
               mon = new KvProgressMonitor(title);
               mon.setNote(message);
               mon.hideCancelButton();
               mon.setModal(true);
               mon.setProgress(1);
            }
            catch (Exception ex) {
               CAT.error("getPressedOption: ", ex);
            }
         }
         return true;
      }
   }

   public void valueChanged(ValueChangedEvent event) {
      boolean startDownload;
      int errorCode;
      String title, message;

      try {
         startDownload = event.getVariable().getBooleanValue();
         btnDownloadToModem.repaint();
         if (startDownload == false && mon != null) {
            mon.close();
            mon = null;
            try {
               HmiVariableService.getService().readValue(m_varErrorCode);
               errorCode = m_varErrorCode.getIntValue(); 
            }
            catch (Exception varEx) {
               CAT.error("valueChanged (varEx): ", varEx);
               errorCode = 0;
            }
            if (errorCode == 0) {
               try {
                  title = m_bundleDlg.getString("download_ok_title");
                  message = m_bundleDlg.getString("download_ok_message");
               }
               catch (Exception ex) {
                  CAT.error("valueChanged (ok): ", ex);
                  title = "Modem SIM data";
                  message = "SIM data downloaded successfully!";
               }
               KvMessageDialog.showInfoMessage(message, title, true, null);
            }
            else {   // errorCode > 0
               try {
                  title = m_bundleDlg.getString("download_failed_title");
                  message = m_bundleDlg.getString("download_error_code_" + fillWithZeros(errorCode));
               }
               catch (Exception ex) {
                  CAT.error("valueChanged (failed): ", ex);
                  title = "SIM data download failed!";
                  message = "download_error_code_" + fillWithZeros(errorCode);
               }
               KvMessageDialog.showErrorMessage(message, title, true, null);
            }
         }         
      }
      catch (Exception ex) {
         CAT.error("valueChanged: ", ex);
      }
   }
   
   private String fillWithZeros(int errorCode) {
      String retCode;
      int length;
      
      retCode = Integer.toString(errorCode);
      length = retCode.length();
      for (int i=length; i<3; i++) {
         retCode = "0" + retCode;
      }
      return retCode;
   }
   
   protected void changeLocale(Locale locale) {
      super.changeLocale(locale);
      m_bundleDlg = ResourceBundle.getBundle(RESOURCE_BUNDLE_DLG, locale);
   }
}
