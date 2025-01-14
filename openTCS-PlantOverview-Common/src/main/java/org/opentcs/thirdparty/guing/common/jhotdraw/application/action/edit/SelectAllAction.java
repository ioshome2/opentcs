/**
 * (c): IML, JHotDraw.
 *
 */
package org.opentcs.thirdparty.guing.common.jhotdraw.application.action.edit;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import static javax.swing.Action.ACCELERATOR_KEY;
import static javax.swing.Action.LARGE_ICON_KEY;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;
import org.opentcs.guing.common.components.EditableComponent;
import static org.opentcs.guing.common.util.I18nPlantOverview.MENU_PATH;
import org.opentcs.guing.common.util.ImageDirectory;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 * Selects all items.
 * This action acts on the last EditableComponent / {@code JTextComponent}
 * which had the focus when the {@code ActionEvent} was generated.
 * This action is called when the user selects the "Select All" item in the
 * Edit menu. The menu item is automatically created by the application.
 *
 * @author Werner Randelshofer.
 */
public class SelectAllAction
    extends org.jhotdraw.app.action.edit.AbstractSelectionAction {

  /**
   * This action's ID.
   */
  public static final String ID = "edit.selectAll";

  private static final ResourceBundleUtil BUNDLE = ResourceBundleUtil.getBundle(MENU_PATH);

  /**
   * Creates a new instance which acts on the currently focused component.
   */
  public SelectAllAction() {
    this(null);
  }

  /**
   * Creates a new instance which acts on the specified component.
   *
   * @param target The target of the action. Specify null for the currently
   * focused component.
   */
  public SelectAllAction(JComponent target) {
    super(target);

    putValue(NAME, BUNDLE.getString("selectAllAction.name"));
    putValue(SHORT_DESCRIPTION, BUNDLE.getString("selectAllAction.shortDescription"));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl A"));

    ImageIcon icon = ImageDirectory.getImageIcon("/menu/edit-select-all.png");
    putValue(SMALL_ICON, icon);
    putValue(LARGE_ICON_KEY, icon);
  }

  @Override
  public void actionPerformed(ActionEvent evt) {
    JComponent cTarget = target;
    Component cFocusOwner
        = KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();

    if (cTarget == null && (cFocusOwner instanceof JComponent)) {
      cTarget = (JComponent) cFocusOwner;
    }

    if (cTarget != null && cTarget.isEnabled()) {
      if (cTarget instanceof EditableComponent) {
        ((EditableComponent) cTarget).selectAll();
      }
      else if (cTarget instanceof JTextComponent) {
        ((JTextComponent) cTarget).selectAll();
      }
      else {
        cTarget.getToolkit().beep();
      }
    }
  }

  @Override
  protected void updateEnabled() {
    if (target != null) {
      setEnabled(target.isEnabled());
    }
  }
}
