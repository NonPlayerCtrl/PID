/*-------------------------------------------------------------------------
 *                   (c) 2003 by KEBA Ges.m.b.H & Co
 *                            Linz/AUSTRIA
 *                         All rights reserved
 *--------------------------------------------------------------------------
 *    Projekt   : Injection Mold Machine
 *    Erstautor : azb
 *    Datum     : 07.07.2003
 *    $Author: Azb $
 *    $Revision: 5 $
 *    $Modtime: 16.10.07 14:00 $
 *    $Archive: /kunden/TIG/KVS/com/keba/kemro/kvs/keplast/recipe/RecipeMask.java $
 *-------------------------------------------------------------------------- */

package ServiceNet.hmi;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Locale;
import com.ibm.log4j.Category;
import com.keba.kemro.kv.util.KvConstants;
import com.keba.kemro.kv.widgets.KvMaskChangeButton;
import com.keba.kemro.kv.widgets.KvPanel;
import com.keba.kemro.kvs.control.KvsUserManager;
import com.keba.kemro.kvs.keplast.recipe.AbsRecipeMask;
import com.keba.kemro.kvs.keplast.recipe.util.DataStateManager;
import com.keba.kemro.kvs.keplast.tig.TIG;
import com.keba.kemro.kvs.keplast.tig.dataset.TIGDataSetMask;
import com.keba.kemro.kvs.recipe.data.DefaultKPVarGroupStorage;
import com.keba.kemro.kvs.recipe.framework.PathSelectBox;
import com.keba.kemro.plc.service.AbstractVarGroupStorage;
import com.keba.keplast.general.PlastConstants;

/*******************************************************************************
 * This class defines the simlist mask used for the ServiceNet.
 * 
 * @since 25.06.2012
 * @author tur
 ******************************************************************************/
public class ServiceNetSimListMask extends AbsRecipeMask {
   public static final String sVAR_SIMLISTDATANAME = "ServiceNet.sv_sSIMDataName";
   protected static final String sRESOUCRE = "ServiceNet.hmi.text.ServiceNetSimListMask_texts";
   private Category CAT;
   private DefaultKPVarGroupStorage m_varGroupStorage;

   private TIGDataSetMask tigPanel = null;

   /*****************************************************************************
    * Constructs a new RecipeMask.
    ****************************************************************************/
   public ServiceNetSimListMask() {
      super("ModemSimData", new PathSelectBox(TIG.getInstance().isTIGExisting()));

      CAT = Category.getInstance(getClass().getName());
      try {
         this.setTitle(textBundle.getString("this.setTitle"));
         this.setDisplayLevel(PlastConstants.LEVEL_SERVICE);
         this.setInputLevel(PlastConstants.LEVEL_SERVICE);
         this.setIconName("system/hmi/images/maskicons/iconServiceNetSimList.gif");
         if (TIG.getInstance().isTIGExisting()) {
            tigPanel = new TIGDataSetMask(createScrollPane());
            panelCard.add(tigPanel, "TIG");
   
            tigPanel.changeLocale(Locale.getDefault(), textBundle.getResBundle());
         }
         recipeFramework.getRecipeTable().setResourceBundle(sRESOUCRE);
         recipeFramework.getRecipeTable().changeLocale(KvsUserManager.getSharedInstance().getCurrentUser().getLocale());

         KvPanel buttonPanel;
         buttonPanel = (KvPanel)buttonFind.getParent();
         buttonPanel.remove(buttonFind);
         KvMaskChangeButton btnServiceOverview = new KvMaskChangeButton();
         btnServiceOverview.setEnableChangingPropertiesImplicit(false);
         btnServiceOverview.setMask("ServiceNet.hmi.ServiceNetOverviewMask");
         btnServiceOverview.setUseMaskImage(true);
         btnServiceOverview.setUseMaskText(true);
         buttonPanel.add(btnServiceOverview);
      }
      catch (Exception ex) {
         CAT.error("Constructor: ", ex);
      }
   }

   protected int getDataStateRead() {
      // no explicit data state change with SIM lists
      return DataStateManager.DS_READY;
   }

   protected int getDataStateWrite() {
      // no explicit data state change with SIM lists
      return DataStateManager.DS_READY;
   }

   /**
    * Returns the name of the mold data systemvariable
    * 
    * @return SV name
    */
   protected String getVariableDataName() {
      return sVAR_SIMLISTDATANAME;
   }

   /**
    * Returns a new MoldDataVarGroupStorage object
    * 
    * @return
    */
   protected AbstractVarGroupStorage getVarGroupStorage() {
      if (m_varGroupStorage == null) {
         m_varGroupStorage = new DefaultKPVarGroupStorage();
         m_varGroupStorage.setVarGroupName("VG_ServiceNetConnectionData");
      }
      return m_varGroupStorage;
   }

   /**
    * Returns the name of the resource bundle
    * 
    * @return string
    */
   protected String getResourceBundleName() {
      return sRESOUCRE;
   }

   /**
    * Action perform for drive selection
    * 
    * @param e
    */
   public void actionPerformedDriveSelect(ActionEvent e) {
      if (driveSelectList.isTIGHostSelected()) {
         cardLayout.last(panelCard);
      }
      else {
         cardLayout.first(panelCard);
         super.actionPerformedDriveSelect(e);
      }
   }

   /**
    * Called if language was changed
    * 
    * @param loc
    */
   public void changeLocale(Locale loc) {
      super.changeLocale(loc);

      if (tigPanel != null) {
         tigPanel.changeLocale(loc, textBundle.getResBundle());
      }
   }

   public void doLayout() {
      super.doLayout();
      Insets insets = recipeFramework.getRecipeTable().getInsets();
      int wRest = getSize().width - insets.left - insets.right - KvConstants.SCROLL_BUT_W;
      int[] widths = { wRest, 0, 0, 0 };
      recipeFramework.getRecipeTable().setPreferredWidths(widths);
   }
}
