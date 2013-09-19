package cyberworld;

import com.jme3.ai.navmesh.NavMesh;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * CyberWorld
 *
 * @author zDemoniac
 */
public class Game extends SimpleApplication implements ScreenController {

    private Nifty nifty;
    private Player player;
    private Player computer;
    private TextRenderer guiEnergy;
    private TextRenderer guiSelected;
    private TextRenderer guiHealth;
    private Element guiBuildUnit;
    private CheckBox guiCheckChassis1;
    private CheckBox guiCheckTorso1;
    private CheckBox guiCheckGun1;
    
    String selectedName = new String();
    
    private NavMesh navMesh;
    
    private AI ai;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1024, 720);
        settings.setVSync(true);
        settings.setSamples(4);
        settings.setTitle("::Cyber World::");
        Game app = new Game();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        inputManager.setCursorVisible(true);
        flyCam.setEnabled(false);

        player = new Player(new ColorRGBA(0, 1, 0, 1), 7, "baseGreen", this);
        computer = new Player(new ColorRGBA(1, 0, 0, 1), 7, "baseRed", this);
        ai = new AI(computer, player);

        initScene();
        initKeys(); // load my custom keybinding
        
        //init shadows
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-30f, -60f, 0f));
        rootNode.addLight(sun);
        
        /* Drop shadows */
        final int SHADOWMAP_SIZE = 512;
        final int SHADOWMAP_NBSPLITS = 1;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(
                assetManager, SHADOWMAP_SIZE, SHADOWMAP_NBSPLITS);
        dlsr.setLight(sun);
        dlsr.setShadowIntensity(0.2f);
        dlsr.setEdgesThickness(20);
        viewPort.addProcessor(dlsr);
 
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(
                assetManager, SHADOWMAP_SIZE, SHADOWMAP_NBSPLITS);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(dlsf);
        viewPort.addProcessor(fpp);

        // init GUI
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/addUnit.xml", "start", this);
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
        Screen screen = nifty.getCurrentScreen();

        guiEnergy = screen.findElementByName("energy").getRenderer(TextRenderer.class);
        guiSelected = screen.findElementByName("selected").getRenderer(TextRenderer.class);
        guiHealth = screen.findElementByName("health").getRenderer(TextRenderer.class);
        guiBuildUnit = screen.findElementByName("buildUnit");
        guiBuildUnit.hideWithoutEffect();
        guiCheckChassis1 = screen.findNiftyControl("chassis1", CheckBox.class);
        guiCheckTorso1 = screen.findNiftyControl("torso1", CheckBox.class);
        guiCheckGun1 = screen.findNiftyControl("gun1", CheckBox.class);
    }

    private void addBase(String name, Player pl, int color) {
        //System.out.println("addBase for " + name);
        Spatial spawn = rootNode.getChild(name + ".Spawn");
        if (spawn != null) {
            pl.addBase(name, spawn.getLocalTranslation(), color);
        } else {
            System.out.println("no spawn for" + name);
        }
    }

    private void initScene() {
        final CameraNode camNode = new CameraNode("Camera Node", cam);

        Spatial scene = assetManager.loadModel("Scenes/newScene.j3o");

        SceneGraphVisitor sgv = new SceneGraphVisitor() {
            @Override
            public void visit(Spatial spatial) {
                if (spatial instanceof Geometry) {
                    return;
                }
                //System.out.println(spatial);
                String name = spatial.getName();
                if (name.contains(".")) {
                    return;
                }

                if (name.contains(player.getBaseNamePrefix())) {
                    addBase(name, player, 0x00ff00);
                } else if (name.contains(computer.getBaseNamePrefix())) {
                    addBase(name, computer, 0xff0000);
                }
            }
        };

        rootNode.attachChild(scene);
        rootNode.depthFirstTraversal(sgv);  // read special info

        // read camera
        camNode.setLocalTranslation(rootNode.getChild("CameraMain").getLocalTranslation());
        camNode.lookAt(rootNode.getChild("CameraTarget").getLocalTranslation(), Vector3f.UNIT_Y);
        rootNode.attachChild(camNode);
        
        Geometry navMeshGeom = (Geometry)rootNode.getChild("NavMesh");
        navMesh = new NavMesh(navMeshGeom.getMesh());
    }

    private void initKeys() {
        // You can map one or several inputs to one named action
        //inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        // Add the names to the action listener.
        inputManager.addListener(analogListener, "click");
    }

    @Override
    public void simpleUpdate(float tpf) {
        player.update(tpf);
        computer.update(tpf);
        ai.update(tpf);

        // update gui
        guiEnergy.setText(Integer.toString((int)player.getEnergy()));
        
        Node selectedObj = player.getSelectedObject();
        if(selectedObj != null && selectedObj.getName().contains("Unit")) {
            guiHealth.setText(Integer.toString((int)player.getSelectedUnit().getHealth()));
        }
    }

    public void onAddUnitClick() {
        String parts = "";
        if (guiCheckChassis1.isChecked()) {
            parts += "chassis1";
        }
        else {
            System.err.println("no chassis!");
            return;
        }
        if(guiCheckTorso1.isChecked()) parts += ",torso1";
        if(guiCheckGun1.isChecked()) parts += ",gun1";
        
        player.addUnit(parts.split(","));
    }
    
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public NavMesh getNavMesh() {
        return navMesh;
    }
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {
            //System.out.println("onAnalog");
            if (name.equals("click")) {
                // Reset results list.
                CollisionResults results = new CollisionResults();
                // Convert screen click to 3d position
                Vector2f click2d = inputManager.getCursorPosition();
                Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
                // Aim the ray from the clicked spot forwards.
                Ray ray = new Ray(click3d, dir);
                // Collect intersections between ray and all nodes in results list.
                rootNode.collideWith(ray, results);
                // (Print the results so we see what is going on:)
//                for (int i = 0; i < results.size(); i++) {
//                    float dist = results.getCollision(i).getDistance();
//                    Vector3f pt = results.getCollision(i).getContactPoint();
//                    String target = results.getCollision(i).getGeometry().getParent().getName();
//                    System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
//                }
                
                if(player.getSelectedObject() != null)
                    selectedName = player.getSelectedObject().getName();
                
                if (results.size() > 0) {
                    //Geometry target = results.getClosestCollision().getGeometry();
                    //target.getMaterial().getAdditionalRenderState().setWireframe(true);
                    Node target = results.getClosestCollision().getGeometry().getParent();
                    Vector3f pos = results.getClosestCollision().getContactPoint();
                    
                    if (selectedName.contains("Unit") && target.getName().contains("Floor")) {
                        System.out.println("GO!");
                        player.getSelectedUnit().goTo(pos);
                    }
                    else {
                        //player.deselectAll();
                        //computer.deselectAll();

                        player.setSelectedObject(target);
                        if(target.getName().contains(player.getBaseNamePrefix())) {
                            guiBuildUnit.showWithoutEffects();
                        }
                        else {
                            guiBuildUnit.hideWithoutEffect();
                        }
                        //var data = that.player.selectedObject.userData;

                        //if(data.hasOwnProperty("select")) data.select(true);

                        //if (!that.player.selectedObject.name.indexOf(that.player.baseName))
                        //    that.player.selectedBase = data;
                    }

                }
                String selectedText = "none";
                if(selectedName.contains("base")) selectedText = "Base";
                else if(selectedName.contains("Unit")) selectedText = "Unit";
                guiSelected.setText(selectedText);
            } // else if ... keyboard and so TODO
        }
    };

    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void quit() {
        System.out.println("end");
        //nifty.gotoScreen("end");
    }
}
