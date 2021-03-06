/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui.factory.diagram;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.archimatetool.editor.diagram.editparts.diagram.GroupEditPart;
import com.archimatetool.editor.ui.ColorFactory;
import com.archimatetool.editor.ui.IArchimateImages;
import com.archimatetool.editor.ui.factory.AbstractElementUIProvider;
import com.archimatetool.model.IArchimatePackage;



/**
 * Group UI Provider
 * 
 * @author Phillip Beauvoir
 */
public class GroupUIProvider extends AbstractElementUIProvider {

    public EClass providerFor() {
        return IArchimatePackage.eINSTANCE.getDiagramModelGroup();
    }
    
    @Override
    public EditPart createEditPart() {
        return new GroupEditPart();
    }

    @Override
    public String getDefaultName() {
        return Messages.GroupUIProvider_0;
    }

    @Override
    public Dimension getDefaultSize() {
        return new Dimension(400, 140);
    }
    
    @Override
    public Image getImage() {
        return IArchimateImages.ImageFactory.getImage(IArchimateImages.ICON_GROUP_16);
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return IArchimateImages.ImageFactory.getImageDescriptor(IArchimateImages.ICON_GROUP_16);
    }

    @Override
    public Color getDefaultColor() {
        return ColorFactory.get(210, 215, 215);
    }
    
    @Override
    public boolean shouldExposeFeature(EObject instance, EAttribute feature) {
        if(feature == IArchimatePackage.Literals.TEXT_ALIGNMENT__TEXT_ALIGNMENT) {
            return false;
        }

        return super.shouldExposeFeature(instance, feature);
    }

}
