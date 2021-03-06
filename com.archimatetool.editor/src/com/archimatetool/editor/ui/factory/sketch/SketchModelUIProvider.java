/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui.factory.sketch;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.archimatetool.editor.diagram.sketch.editparts.SketchDiagramPart;
import com.archimatetool.editor.diagram.sketch.figures.SketchModelGraphicsIcon;
import com.archimatetool.editor.ui.IArchimateImages;
import com.archimatetool.editor.ui.IGraphicsIcon;
import com.archimatetool.editor.ui.factory.AbstractElementUIProvider;
import com.archimatetool.model.IArchimatePackage;



/**
 * Sketch Diagram Model UI Provider
 * 
 * @author Phillip Beauvoir
 */
public class SketchModelUIProvider extends AbstractElementUIProvider {
    
    private IGraphicsIcon graphicsIcon = new SketchModelGraphicsIcon();

    public EClass providerFor() {
        return IArchimatePackage.eINSTANCE.getSketchModel();
    }
    
    @Override
    public EditPart createEditPart() {
        return new SketchDiagramPart();
    }
    
    @Override
    public String getDefaultName() {
        return Messages.SketchModelUIProvider_0;
    }

    @Override
    public Image getImage() {
        return IArchimateImages.ImageFactory.getImage(IArchimateImages.ICON_SKETCH_16);
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return IArchimateImages.ImageFactory.getImageDescriptor(IArchimateImages.ICON_SKETCH_16);
    }
    
    @Override
    public IGraphicsIcon getGraphicsIcon() {
        return graphicsIcon;
    }
}
