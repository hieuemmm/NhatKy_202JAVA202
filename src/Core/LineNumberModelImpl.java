/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import GUI.FormNhatKy;
import java.awt.Rectangle;
import javax.swing.text.BadLocationException;

public class LineNumberModelImpl implements LineNumberModel {
    @Override
    public int getNumberLines() {
        try {
            return FormNhatKy.jTextAreaDiary.getLineCount();
        } catch (Exception e) {
            
        }
        return 0;
    }
    @Override
    public Rectangle getLineRect(int line) {
        try {
            return FormNhatKy.jTextAreaDiary.modelToView(FormNhatKy.jTextAreaDiary.getLineStartOffset(line));
        } catch (BadLocationException e) {
            e.printStackTrace();
            return new Rectangle();
        }
    }
}
