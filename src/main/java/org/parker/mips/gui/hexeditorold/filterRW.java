package org.parker.mips.gui.hexeditorold;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class filterRW extends FileFilter {

   public boolean accept(File var1) {
      return var1.canWrite();
   }

   public String getDescription() {
      return "";
   }
}
