import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.android.chimpchat.*;
import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.core.*;
import com.android.chimpchat.hierarchyviewer.HierarchyViewer;

import org.graphwalker.exceptions.InvalidDataException;
import org.graphwalker.generators.PathGenerator;
import org.testng.Assert;
import org.graphwalker.multipleModels.ModelAPI;
import com.android.uiautomator.*;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class AcessAndy extends ModelAPI {

	/**
	 * @param args
	 */
	
	AdbBackend adb = null;
	IChimpDevice device = null;
	ChimpManager manager=null;


	

	  public AcessAndy(File model, boolean efsm, PathGenerator generator, boolean weight) {
		    super(model, efsm, generator, weight);
		  }
	  
	  

	  /**
	   * This method implements the Edge 'e_NewGame'
	   * 
	   */
	  /*public void e_NewGame() {
		  try {
				boolean ng=manager.tap(400,150);

			
				System.out.println("New Game pressed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }*/


	  /**
	   * This method implements the Edge 'e_PressOptions'
	 * @throws IOException 
	   * 
	   */
	  public void e_PressOptions() throws IOException {
		  try {
				boolean op=manager.tap(240,250);
				System.out.println("Optiones presed");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }


	  /**
	   * This method implements the Edge 'e_back'
	   * 
	   */
	  public void e_back() {
			try {
				boolean bkop=manager.tap(400,350);
				System.out.println("Back to aptions pressed");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	  }


	  /**
	   * This method implements the Edge 'e_escape'
	   * 
	   */
	  public void e_bkMain() {
			System.out.println("Back to main Menu pressed");

	  }


	  /**
	   * This method implements the Edge 'e_loading'
	   * 
	   */
	  public void e_loading() {
		  adb=new AdbBackend();
		  device=adb.waitForConnection();
		manager=  device.getManager();
		  Assert.assertNotNull(device);
			try {
				Thread.sleep(5000);
				System.out.println("Game Loaded");

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




		  
	  }


	  /**
	   * This method implements the Edge 'e_pressLang'
	   * 
	   */
	  public void e_pressLang() {
		  try {
				boolean lng=manager.tap(400,320);
				try {
					Thread.sleep(5000);
					System.out.println("Language  pressed");

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
	  }


	  /**
	   * This method implements the Edge 'e_pressMusic'
	   * 
	   */
	  public void e_pressMusic() {
		  try {
				boolean msc=manager.tap(400,150);
				try {
					Thread.sleep(5000);
					System.out.println("Musi pressed");

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	  }


	  /**
	   * This method implements the Edge 'e_pressOptions'
	   * 
	   */
	  public void e_pressSound() {
		  try {
				boolean snd=manager.tap(400,260);
				try {
					Thread.sleep(5000);
					System.out.println("Sound  pressed");

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	  }


	  /**
	   * This method implements the Vertex 'v_FirstPuzzle'
	   * 
	   */
	  public void v_FirstPuzzle() {
	  }


	  /**
	   * This method implements the Vertex 'v_GameMenu'
	   * 
	   */
	  public void v_GameMenu() {
	  }


	  /**
	   * This method implements the Vertex 'v_Language'
	   * 
	   */
	  public void v_Language() {
			

	  }


	  /**
	   * This method implements the Vertex 'v_Music'
	   * 
	   */
	  public void v_Music() {
			
	  }


	  /**
	   * This method implements the Vertex 'v_OptionsMenu'
	   * 
	   */
	  public void v_OptionsMenu() {
			

	  }


	  /**
	   * This method implements the Vertex 'v_Sound'
	   * 
	   */
	  public void v_Sound() {
			

	  }


	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  

	/*public static void main(String[] args) {
				
		AdbBackend adb = new AdbBackend();

		
		IChimpDevice device = adb.waitForConnection();

		ChimpManager manager=device.getManager();
		
		try {
				boolean snd=manager.tap(400,260);
				System.out.println("Sound  pressed");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		IChimpImage img=device.takeSnapshot();
		//img.writeToFile("d:\\mbt\\puzzle.png", "png");

	 BufferedImage image=img.createBufferedImage();
		int width=image.getWidth();
		int height=image.getHeight();
		int wsplit=(width/2);
		int hsplit=(height/10);
		

		System.out.println("Dawoud");
		
		
		
		 try {

				//boolean scr=manager.touchDown(240,200);

				//boolean hlp=manager.touchDown(240,350);
				 // boolean qt=manager.touchDown(240,400);


				
				//device.drag(10, 50, 100, 150, 200, 400);
				//manager.touchDown(210,600);





			//	manager.tap(200,200);
			//	manager.tap(200,200);

				
				
				
			//	System.out.println(options+""+wsplit+""+(hsplit*5));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			System.out.println(manager.listViewIds());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		


		
		//System.out.println(wsplit+""+hsplit);

		//((wsplit*4)+"\t"+(hsplit*4));

 device.dispose();


	}*/

}
