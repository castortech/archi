/*******************************************************************************
 * Copyright (c) 2010 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 *******************************************************************************/
package uk.ac.bolton.archimate.editor.diagram.sketch.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import uk.ac.bolton.archimate.editor.diagram.figures.AbstractLabelFigure;
import uk.ac.bolton.archimate.editor.diagram.figures.ToolTipFigure;
import uk.ac.bolton.archimate.editor.preferences.Preferences;
import uk.ac.bolton.archimate.editor.utils.StringUtils;
import uk.ac.bolton.archimate.model.ISketchModelActor;


/**
 * Sketch Actor Figure
 * 
 * @author Phillip Beauvoir
 */
public class SketchActorFigure extends AbstractLabelFigure {

    static Dimension DEFAULT_SIZE = new Dimension(75, 100);
    
    public SketchActorFigure(ISketchModelActor actor) {
        super(actor);
    }
    
    @Override
    protected void paintFigure(Graphics graphics) {
        super.paintFigure(graphics);
        
        graphics.setForegroundColor(getFillColor());
        
        graphics.setAntialias(SWT.ON);
        Rectangle bounds = getBounds().getCopy();
        
        graphics.setLineWidth(2);
        
        bounds.height -= getLabel().getPreferredSize().height;
        
        int narrowest = Math.min(bounds.width, bounds.height);
        
        int diameter = narrowest / 3;
        int midX = bounds.x + bounds.width / 2;
        
        // Head
        graphics.drawOval(midX - (diameter / 2), bounds.y + 1, diameter, diameter);
        
        // Body
        int bodyY2 = bounds.y + bounds.height - (bounds.height / 3);
        graphics.drawLine(midX, bounds.y + diameter, midX, bodyY2);
        
        // Arms
        int armsY = bounds.y + diameter + (bounds.height / 8);
        graphics.drawLine(midX - diameter, armsY, midX + diameter, armsY);
        
        // Legs
        graphics.drawLine(midX, bodyY2, midX - diameter, bounds.y + bounds.height);
        graphics.drawLine(midX, bodyY2, midX + diameter, bounds.y + bounds.height);
    }

    @Override
    public Dimension getDefaultSize() {
        return DEFAULT_SIZE;
    }
    
    @Override
    public Dimension getPreferredSize(int wHint, int hHint) {
        return getDefaultSize();
    }

    @Override
    public Rectangle calculateTextControlBounds() {
        Rectangle bounds = getBounds().getCopy();
        bounds.y += bounds.height - getLabel().getPreferredSize().height;
        bounds.height = getLabel().getPreferredSize().height;
        
        return bounds;
    }

    @Override
    protected void setToolTip() {
        if(!Preferences.doShowViewTooltips()) {
            setToolTip(null); // clear it in case user changed Prefs
            return;
        }

        if(getToolTip() == null) {
            setToolTip(new ToolTipFigure());
        }

        String text = StringUtils.safeString(getDiagramModelObject().getName());
        ((ToolTipFigure)getToolTip()).setText(text);
        ((ToolTipFigure)getToolTip()).setType("Type: Actor");
    }
}
