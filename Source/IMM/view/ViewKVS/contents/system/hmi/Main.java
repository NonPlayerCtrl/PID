package system.hmi;

import java.awt.BorderLayout;
import com.keba.kemro.kv.control.KvMask;

public class Main extends MainBase {

   private static final long serialVersionUID = 1L;

   protected static String sIMAGE_MAXIMIZE_MASK = "system/hmi/images/iconMaximizeMask.gif";
   protected static String sIMAGE_MINIMIZE_MASK = "system/hmi/images/iconMinimizeMask.gif";

   private boolean bMaximized = false;
   private boolean bMaximizeAllowed;

   public Main() throws Exception {
      super();
   }

   protected boolean showPopupMask(KvMask mask) {
      boolean bShowPopup = super.showPopupMask(mask);
      if (!bShowPopup) {
         boolean maskChange = mask != null && !mask.equals(getCurrentMask());
         showMaskFrame(mask);
         // update minimize / maximize button
         if (null != mask) {
            bMaximizeAllowed = mask.getMaximize();
            if (maskChange) {
               buttonMinimizeMaximize.setIconName(sIMAGE_MAXIMIZE_MASK);
            }
            if (!bMaximizeAllowed) {
               setMaskMaximized(false);
            }
            buttonMinimizeMaximize.setEnabled(bMaximizeAllowed);
            buttonMinimizeMaximize.repaint();
         }
      }
      return bShowPopup;
   }

   /**
    * Method for maximize / minimize button action only used on a portrait panel system makes the
    * actual mask full size or half size and changes the button image
    */
   public void cmdMinimizeMaximizeMask() {
      setMaskMaximized(!bMaximized);
   }

   /**
    * Sets the visibility of the north panel on an portrait system
    * 
    * @param b
    */
   void setMaskMaximized(boolean bMaskMaximized) {
      // set panelInfoState visible and move
      if (bMaskMaximized) {
         containerEastContent.add(panelMoveAndCoreState, BorderLayout.NORTH);
         buttonMinimizeMaximize.setIconName(sIMAGE_MINIMIZE_MASK);
         panelInfoState.setVisible(false);
      }
      else {
         panelMoveCoreStateMinimized.add(panelMoveAndCoreState, BorderLayout.CENTER);
         buttonMinimizeMaximize.setIconName(sIMAGE_MAXIMIZE_MASK);
         panelInfoState.setVisible(true);
      }
      bMaximized = bMaskMaximized;
      validate();
   }

}
