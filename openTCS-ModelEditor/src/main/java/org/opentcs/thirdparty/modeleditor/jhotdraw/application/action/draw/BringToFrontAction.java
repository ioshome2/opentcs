/**
 * (c): IML, JHotDraw.
 *
 * Changed by IML to allow access to ResourceBundle.
 *
 *
 * @(#)BringToFrontAction.java
 *
 * Copyright (c) 2003-2008 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.opentcs.thirdparty.modeleditor.jhotdraw.application.action.draw;

import java.util.Collection;
import java.util.LinkedList;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.opentcs.guing.common.util.ImageDirectory;
import static org.opentcs.modeleditor.util.I18nPlantOverviewModeling.TOOLBAR_PATH;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 * ToFrontAction.
 *
 * @author Werner Randelshofer
 */
public class BringToFrontAction
    extends AbstractSelectedAction {

  /**
   * This action's ID.
   */
  public static final String ID = "edit.bringToFront";

  private static final ResourceBundleUtil BUNDLE = ResourceBundleUtil.getBundle(TOOLBAR_PATH);

  /**
   * Creates a new instance.
   *
   * @param editor The drawing editor
   */
  public BringToFrontAction(DrawingEditor editor) {
    super(editor);

    putValue(NAME, BUNDLE.getString("bringToFrontAction.name"));
    putValue(SHORT_DESCRIPTION, BUNDLE.getString("bringToFrontAction.shortDescription"));
    putValue(SMALL_ICON, ImageDirectory.getImageIcon("/toolbar/object-order-front.png"));

    updateEnabledState();
  }

  @Override
  public void actionPerformed(java.awt.event.ActionEvent e) {
    final DrawingView view = getView();
    final LinkedList<Figure> figures = new LinkedList<>(view.getSelectedFigures());
    bringToFront(view, figures);
    fireUndoableEditHappened(new AbstractUndoableEdit() {
      @Override
      public String getPresentationName() {
        return ResourceBundleUtil.getBundle(TOOLBAR_PATH)
            .getString("bringToFrontAction.undo.presentationName");
      }

      @Override
      public void redo()
          throws CannotRedoException {
        super.redo();
        BringToFrontAction.bringToFront(view, figures);
      }

      @Override
      public void undo()
          throws CannotUndoException {
        super.undo();
        SendToBackAction.sendToBack(view, figures);
      }
    });
  }

  public static void bringToFront(DrawingView view, Collection<Figure> figures) {
    Drawing drawing = view.getDrawing();

    for (Figure figure : drawing.sort(figures)) {
      drawing.bringToFront(figure);
    }
  }
}
