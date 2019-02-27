package ServiceNet.hmi;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import com.ibm.log4j.Category;
import com.keba.espresso.awt.config.rtk.KvTextFieldBackgroundRendererInterface;
import com.keba.kemro.kv.util.KvToolkit;
import com.keba.kemro.kv.widgets.event.KvVirtualKeyboardListener;
import com.keba.kemro.plc.event.ValueChangedEvent;
import com.keba.kemro.plc.event.ValueChangedListener;
import com.keba.kemro.plc.service.HmiVariableService;
import com.keba.kemro.plc.variable.KVariable;
import com.keba.kemro.plc.variable.VartypeException;
import com.keba.util.KChangeEvent;
import espial.awt.Border;

public class ServiceNetOverviewMask extends ServiceNetOverviewMask_generated {
   private static final long serialVersionUID = 1L;
   private final int NR_OF_CONTACTS = 3;
   private final int NR_OF_REQUESTFROM = 3;
   private final String ACTION_CMD_REQUEST = "doRequest";
   private final String ACTION_CMD_DISCONNECT = "doDisconnect";
   private final String VAR_PATH_CONTACTTO_INDEX = "ServiceNet.sv_iContactToIdx";
   private final String VAR_PATH_REQUESTFROM_INDEX = "ServiceNet.sv_iRequestFromIdx";
   private final String VAR_PATH_MESSAGE_TEXT = "ServiceNet.sv_sTextMessage";
   private final String VAR_PATH_CONNECT_CMD = "ServiceNet.sv_bConnect";
   private KVariable m_varIdxContactTo;
   private KVariable m_varIdxRequestFrom;
   private KVariable m_varMessageText;
   private KVariable m_varConnect;
   private Category CAT;

   public ServiceNetOverviewMask() throws Exception {
      super();
      CAT = Category.getInstance("ServiceNetOverviewMask");
      try {
         initComboBoxes();
         initComponents();
         initVariables();
      }
      catch (Exception ex) {
         CAT.error("Constructor: ", ex);
      }
   }

   private void initComboBoxes() {
      String contactToList, requestFromList;
      String contact, requestFrom;
      HmiVariableService service;
      KVariable varContact, varRequestFrom;
      int selectedIndexContact, selectedIndexRequest;

      selectedIndexContact = comboContactTo.getSelectedIndex();
      selectedIndexRequest = comboRequestFrom.getSelectedIndex();
      contactToList = "";
      requestFromList = "";
      try {
         service = HmiVariableService.getService();
         for (int i = 1; i <= NR_OF_CONTACTS; i++) {
            varContact = service.getVariable("ServiceNet.sv_ContactTo[" + i + "].sName");
            service.readValue(varContact);
            contact = varContact.getStringValue();
            if (contact == null) {
               contact = "Contact " + i;
            }
            else if (contact.length() == 0) {
               contact = "Contact " + i;
            }
            contactToList = contactToList + contact + ";";
         }
         if (contactToList.endsWith(";") == true) {
            contactToList = contactToList.substring(0, contactToList.length() - 1);
         }

         for (int i = 1; i <= NR_OF_REQUESTFROM; i++) {
            varRequestFrom = service.getVariable("ServiceNet.sv_RequestFrom[" + i + "].sName");
            service.readValue(varRequestFrom);
            requestFrom = varRequestFrom.getStringValue();
            if (requestFrom == null) {
               requestFrom = "From " + i;
            }
            else if (requestFrom.length() == 0) {
               requestFrom = "From " + i;
            }
            requestFromList = requestFromList + requestFrom + ";";
         }
         if (requestFromList.endsWith(";") == true) {
            requestFromList = requestFromList.substring(0, requestFromList.length() - 1);
         }
      }
      catch (Exception ex) {
         CAT.error("initComboBoxes", ex);
      }
      comboContactTo.setListTexts(contactToList);
      comboContactTo.setSelectedIndex(selectedIndexContact);
      comboRequestFrom.setListTexts(requestFromList);
      comboRequestFrom.setSelectedIndex(selectedIndexRequest);
   }

   private void initComponents() {
      containerStateShadow.setBorder(new StateBorder());
      btnSimList.setMask("ServiceNet.hmi.ServiceNetSimListMask");

      comboContactTo.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               if (m_varIdxContactTo == null) return;
               HmiVariableService.getService().writeValue(m_varIdxContactTo, new Integer(comboContactTo.getSelectedIndex() + 1));
            }
            catch (Exception ex) {
               CAT.error("actionPerformed (contactTo): ", ex);
            }
         }
      });
      comboRequestFrom.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               if (m_varIdxRequestFrom == null) return;
               HmiVariableService.getService().writeValue(m_varIdxRequestFrom, new Integer(comboRequestFrom.getSelectedIndex() + 1));
            }
            catch (Exception ex) {
               CAT.error("actionPerformed (requestFrom): ", ex);
            }
         }
      });
      tfTextMessage.addVirtualKeyboardListener(new KvVirtualKeyboardListener() {
         
         public void stateChanged(KChangeEvent event) {
            try {
               String newText;
               
               newText = convertForJsonProtocol(tfTextMessage.getText());
               HmiVariableService.getService().writeValue(m_varMessageText, newText);
            }
            catch (Exception ex) {
               CAT.error("stateChanged (MessageText): ", ex);
            }
         }
      });
      
      
      btnRequest.removeActionListener(this);
      btnRequest.addActionListener(new ActionListener() {
         
         public void actionPerformed(ActionEvent e) {
            btnRequestClicked();
         }
      });
   }
   
   private void initVariables() {
      try {
         m_varIdxContactTo = HmiVariableService.registerVariable(VAR_PATH_CONTACTTO_INDEX, new ValueChangedListener() {
            public void valueChanged(ValueChangedEvent event) {
               int selectedIndex;
               
               try {
                  selectedIndex = event.getVariable().getIntValue();
               }
               catch (Exception ex) {
                  CAT.error("valueChanged (changedToIdx): ", ex);
                  selectedIndex = 0;
               }
               comboContactTo.setSelectedIndex(selectedIndex - 1);
               comboContactTo.repaint();
            }
         });
         m_varIdxRequestFrom = HmiVariableService.registerVariable(VAR_PATH_REQUESTFROM_INDEX, new ValueChangedListener() {
            public void valueChanged(ValueChangedEvent event) {
               int selectedIndex;
               
               try {
                  selectedIndex = event.getVariable().getIntValue();
               }
               catch (Exception ex) {
                  CAT.error("valueChanged (requestFromIdx): ", ex);
                  selectedIndex = 0;
               }
               comboRequestFrom.setSelectedIndex(selectedIndex - 1);
               comboRequestFrom.repaint();
            }
         });
         m_varMessageText = HmiVariableService.registerVariable(VAR_PATH_MESSAGE_TEXT, new ValueChangedListener() {
            public void valueChanged(ValueChangedEvent event) {
               String newText;
               
               newText = convertFromJsonString(event.getVariable().getStringValue());
               tfTextMessage.setText(newText);
               tfTextMessage.repaint();
            }
         });
         m_varConnect = HmiVariableService.registerVariable(VAR_PATH_CONNECT_CMD, new ValueChangedListener() {
            public void valueChanged(ValueChangedEvent event) {
               try {
                  checkBtnText(event.getVariable().getBooleanValue());
               }
               catch (Exception ex) {
                  CAT.error("valueChanged (m_varConnect): ", ex);
               }
            }
         });
      }
      catch (Exception ex) {
         CAT.error("initVariables: ", ex);
      }
   }
   
   public void onShow() {
      super.onShow();
      initComboBoxes();
   }
   
   private void btnRequestClicked() {
      String currentActionCommand;
      
      currentActionCommand = btnRequest.getActionCommand();
      if (currentActionCommand.equalsIgnoreCase(ACTION_CMD_REQUEST) == true) {
         try {
            HmiVariableService.getService().writeValue(m_varConnect, Boolean.TRUE);
         }
         catch (Exception ex) {
            CAT.error("btnRequestClicked (true): ", ex);
         }
         btnRequest.setActionCommand(ACTION_CMD_DISCONNECT);
         btnRequest.setText(labDisconnect.getText());
      }
      else {
         try {
            HmiVariableService.getService().writeValue(m_varConnect, Boolean.FALSE);
         }
         catch (Exception ex) {
            CAT.error("btnRequestClicked (false): ", ex);
         }
         btnRequest.setActionCommand(ACTION_CMD_REQUEST);
         btnRequest.setText(labConnect.getText());
      }
      btnRequest.repaint();
   }
   
   private void checkBtnText(boolean connectedCmd) {
      if (connectedCmd == true) {
         btnRequest.setActionCommand(ACTION_CMD_DISCONNECT);
         btnRequest.setText(labDisconnect.getText());
      }
      else {
         btnRequest.setActionCommand(ACTION_CMD_REQUEST);
         btnRequest.setText(labConnect.getText());
      }
      btnRequest.repaint();
   }
   
   private class StateBorder implements Border {

      public void paint(Component c, Graphics g) {
         KvTextFieldBackgroundRendererInterface renderer;
         
         renderer = KvToolkit.getRenderingToolkit().getTextFieldBackgoundRenderer();
         
         renderer.drawFieldBackground(c, g, TEXT_FIELD_NOT_EDITABLE_BG);
         renderer.drawFieldBorder(c, g, COL_WIDGET_BORDER_DISABLED);
      }

      public Insets getInsets(Component arg0) {
         return new Insets(0, 4, 1, 4);
      }
   }

   private String convertForJsonProtocol(String sourceString) {
      String jsonString;
      char ch;
      
      jsonString = "";
      for (int i=0; i<sourceString.length(); i++) {
         ch = sourceString.charAt(i);
         if (ch == '\\') {
            jsonString = jsonString + "\\\\";
         }
         else if (ch == '"') {
            jsonString = jsonString + '\\' + '"';
         }
         else if (ch == '\n') {
            jsonString = jsonString + "\\n";
         }
         else {
            jsonString = jsonString + ch;
         }
      }
      return jsonString;
   }
   
   private String convertFromJsonString(String jsonString) {
      String hmiString;
      char ch;
      char nextCh;
      
      hmiString = "";
      for (int i=0; i<jsonString.length(); i++) {
         ch = jsonString.charAt(i);
         if (ch == '\\') {
            nextCh = jsonString.charAt(i+1);
            if (nextCh == '"') {
               hmiString = hmiString + '"';
            }
            else if (nextCh == '\\') {
               hmiString = hmiString + "\\";
            }
            else if (nextCh == 'n') {
               hmiString = hmiString + '\n';
            }
            else {
               hmiString = hmiString + "\\" + nextCh;
            }
            i = i + 1;
         }
         else {
            hmiString = hmiString + ch;
         }
      }
      return hmiString;
   }
   
   protected void changeLocale(Locale locale) {
      super.changeLocale(locale);
      
      // Correct text for request button depending on current connection
      if (m_varConnect != null) {
         try {
            checkBtnText(m_varConnect.getBooleanValue());
         }
         catch (VartypeException ex) {
            CAT.error("changeLocale (m_varConnect): ", ex);
         }
      }
   }
}

