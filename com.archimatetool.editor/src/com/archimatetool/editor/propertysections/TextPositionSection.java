/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.propertysections;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.archimatetool.editor.model.commands.EObjectFeatureCommand;
import com.archimatetool.model.IArchimatePackage;
import com.archimatetool.model.ILockable;
import com.archimatetool.model.ITextPosition;



/**
 * Property Section for a Text Position
 * 
 * @author Phillip Beauvoir
 */
public class TextPositionSection extends AbstractArchimatePropertySection {
    
    private static final String HELP_ID = "com.archimatetool.help.elementPropertySection"; //$NON-NLS-1$
    
    private static EAttribute FEATURE = IArchimatePackage.Literals.TEXT_POSITION__TEXT_POSITION;
    
    /**
     * Filter to show or reject this section depending on input value
     */
    public static class Filter extends ObjectFilter {
        @Override
        protected boolean isRequiredType(Object object) {
            return (object instanceof ITextPosition) && shouldExposeFeature((EObject)object, FEATURE);
        }

        @Override
        protected Class<?> getAdaptableType() {
            return ITextPosition.class;
        }
    }

    /*
     * Adapter to listen to changes made elsewhere (including Undo/Redo commands)
     */
    private Adapter eAdapter = new AdapterImpl() {
        @Override
        public void notifyChanged(Notification msg) {
            Object feature = msg.getFeature();
            // Model event
            if(feature == FEATURE) {
                refreshControls();
            }
            else if(feature == IArchimatePackage.Literals.LOCKABLE__LOCKED) {
                refreshButtons();
            }
        }
    };
    
    private ITextPosition fTextPosition;
    
    private Combo fComboPositions;
    
    private static final String[] fComboPositionItems = {
        Messages.TextPositionSection_0,
        Messages.TextPositionSection_1,
        Messages.TextPositionSection_2,
        Messages.TextPositionSection_3,
        Messages.TextPositionSection_4,
        Messages.TextPositionSection_5,
        Messages.TextPositionSection_6,
        Messages.TextPositionSection_7,
        Messages.TextPositionSection_8,
    };
    
    
    @Override
    protected void createControls(Composite parent) {
        createLabel(parent, Messages.TextPositionSection_9, ITabbedLayoutConstants.STANDARD_LABEL_WIDTH, SWT.CENTER);
        
        // Position
        fComboPositions = new Combo(parent, SWT.READ_ONLY);
        fComboPositions.setItems(fComboPositionItems);
        fComboPositions.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fComboPositions.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(isAlive()) {
                    fIsExecutingCommand = true;
                    getCommandStack().execute(new EObjectFeatureCommand(Messages.TextPositionSection_10,
                                                fTextPosition,
                                                FEATURE,
                                                fComboPositions.getSelectionIndex()));
                    fIsExecutingCommand = false;
                }
            }
        });
        
        GridData gd = new GridData(SWT.NONE, SWT.NONE, false, false);
        fComboPositions.setLayoutData(gd);
        
        // Help
        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HELP_ID);
    }
    
    @Override
    protected void setElement(Object element) {
        fTextPosition = (ITextPosition)new Filter().adaptObject(element);
        if(fTextPosition == null) {
            System.err.println(getClass() + " failed to get element for " + element); //$NON-NLS-1$
        }
        
        refreshControls();
    }
    
    protected void refreshControls() {
        refreshButtons();
    }
    
    protected void refreshButtons() {
        boolean enabled = fTextPosition instanceof ILockable ? !((ILockable)fTextPosition).isLocked() : true;
        
        int position = fTextPosition.getTextPosition();
        if(position < ITextPosition.TEXT_POSITION_TOP_LEFT || position > ITextPosition.TEXT_POSITION_BOTTOM_RIGHT) {
            position = ITextPosition.TEXT_POSITION_TOP_RIGHT;
        }
        
        if(!fIsExecutingCommand) {
            fComboPositions.select(position);
            fComboPositions.setEnabled(enabled);
        }
    }
    
    @Override
    protected Adapter getECoreAdapter() {
        return eAdapter;
    }

    @Override
    protected EObject getEObject() {
        return fTextPosition;
    }
}
