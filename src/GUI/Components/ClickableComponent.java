package GUI.Components;

import static GUI.GraphicsUtils.GUIMouseListener.attachMouseComponent;

public abstract class ClickableComponent extends Component{


    public boolean highlighted = false;

    public ClickableComponent (int x, int y, int height, int width){
        super(x,y,height,width);
        attachMouseComponent(this);
    }

    public abstract void onClick (int mouseX, int mouseY);
    public abstract void offClick ();
    public abstract void mouseReleased ();
}
