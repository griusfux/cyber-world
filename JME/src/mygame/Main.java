package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 * CyberWorld
 *
 * @author zDemoniac
 */
public class Main extends SimpleApplication {

    Player player;
    Player computer;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1024, 720);
        Main app = new Main();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
//        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
//        Geometry geom = new Geometry("Box", b);
//
//        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.Blue);
//        geom.setMaterial(mat);
        inputManager.setCursorVisible(true);
        flyCam.setEnabled(false);

        player = new Player(7, "baseGreen", rootNode);
        computer = new Player(7, "baseRed", rootNode);

        initScene();
        initKeys(); // load my custom keybinding
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
            public void visit(Spatial spatial) {
                if(spatial instanceof Geometry) return;
                //System.out.println(spatial);
                String name = spatial.getName();
                if(name.contains(".")) return;
                
                if        (name.contains(player.getBaseNamePrefix())) {
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
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {
            System.out.println("onAnalog");
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
                for (int i = 0; i < results.size(); i++) {
                    // (For each “hit”, we know distance, impact point, geometry.)
                    float dist = results.getCollision(i).getDistance();
                    Vector3f pt = results.getCollision(i).getContactPoint();
                    String target = results.getCollision(i).getGeometry().getName();
                    System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
                }
                //         Use the results -- we rotate the selected geometry.
                if (results.size() > 0) {
                    // The closest result is the target that the player picked:
                    Geometry target = results.getClosestCollision().getGeometry();
                    // Here comes the action:
                    target.getMaterial().getAdditionalRenderState().setWireframe(true);
//                    Material cube1Mat = new Material(assetManager, 
//                                        "Common/MatDefs/Misc/Unshaded.j3md");
//                    target.setMaterial(cube1Mat);
                }
            } // else if ...
        }
    };
}
