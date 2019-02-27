/*-------------------------------------------------------------------------
 *                   (c) 2015 by KEBA Ges.m.b.H & Co
 *                            Linz/AUSTRIA
 *                         All rights reserved
 *--------------------------------------------------------------------------
 *    Project  : KePlast.HMI.KVS
 *    Author   : mhf
 *    Date     : 12.05.2015
 *--------------------------------------------------------------------------  *
 */

package system.hmi;

import java.io.File;
import java.util.ResourceBundle;

import com.ibm.log4j.Category;
import com.keba.kemro.kv.dialog.KvProgressMonitor;
import com.keba.keplast.utils.SystemInfo;
import com.keba.kemro.kvs.recipe.manager.KvsRecipeManager;
import com.keba.kemro.kvs.keplast.recipe.util.DataStateManager;
import com.keba.util.Worker;


/***
 * load initial system data (either mold/machine data) from file
 * 
 * @author mhf
 * 
 */
public class StartupAutoLoad {

   protected static Category CAT = Category.getInstance(StartupInfo.class.getName());
   private KvsRecipeManager recipeMgr;
   private File autoLoadPath;   

   public StartupAutoLoad() {
      autoLoadPath = new File(SystemInfo.getInstance().getApplPath(), "workspace\\data\\autoload");
   }
   
   
   public void autoLoad() {      
      
      if (!autoLoadPath.exists())
         return;
      
      Worker w = new Worker() {

         private KvProgressMonitor mon;

         public Object construct() {
            recipeMgr = new KvsRecipeManager("autoLoad");
            recipeMgr.setLoadPath(autoLoadPath.getAbsolutePath());
            
            String title = "";
            try {
               ResourceBundle bundle = ResourceBundle.getBundle("system.hmi.text.startupautoload");
               title = bundle.getString("MsgAutoLoadData");
            }
            catch(Exception ex) {
               CAT.warn("text not found for autoload", ex);
               title = "Auto. loading system data";
            }
            mon = new KvProgressMonitor(title);
            mon.hideCancelButton();
            mon.setModal(true);
            mon.setProgress(1);
            try {            
               String fileNameRel = "machineData\\autoLoad.xml";
               File srcFile = new File(autoLoadPath.getAbsolutePath() + "\\" + fileNameRel);
               if(srcFile.exists()) {               
                  DataStateManager.writeState(DataStateManager.DS_MACHINEDATA_READ);
                  recipeMgr.load(fileNameRel);
                  DataStateManager.writeState(DataStateManager.DS_READY);
               }
               fileNameRel = "molddata\\autoLoad.xml";
               srcFile = new File(autoLoadPath.getAbsolutePath() + "\\" + fileNameRel);
               if(srcFile.exists()) {               
                  DataStateManager.writeState(DataStateManager.DS_MOLDDATA_READ);
                  recipeMgr.load(fileNameRel);
                  DataStateManager.writeState(DataStateManager.DS_READY);
               }
               
            } catch (Exception ex) {               
               CAT.warn(null, ex);
            }
            finally {
               DataStateManager.writeState(DataStateManager.DS_READY);               
            }
            return null;
         }

         public void finished() {
            mon.close();
            deleteFiles();
         }
      };
      w.start();      
   }
   protected void deleteFiles() {
      File renameFile = new File(autoLoadPath + "_used");
      renameFile.delete();
      autoLoadPath.renameTo(renameFile);
   }

 }
