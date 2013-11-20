
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.graphwalker.conditions.EdgeCoverage;
import org.graphwalker.conditions.ReachedVertex;
import org.graphwalker.exceptions.StopConditionException;
import org.graphwalker.generators.A_StarPathGenerator;
import org.graphwalker.generators.RandomPathGenerator;
import org.graphwalker.multipleModels.ModelHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GameTst {

  @Test
  public void a_star() throws InterruptedException, StopConditionException, URISyntaxException {
    ModelHandler modelhandler = new ModelHandler();

    
    File file = new File("d://mbt/mod.graphml");
    Assert.assertNotNull(file,"File Not accessible");
    System.out.println(file.getName());

    
    modelhandler.add("Game", new AcessAndy(file, true, new A_StarPathGenerator(new EdgeCoverage(1.0)), false));

    // Start executing the test
    modelhandler.execute("Game");

    // Verify that the execution is complete, fulfilling the criteria from above.
   AssertJUnit.assertTrue("Not all models are done",modelhandler.isAllModelsDone());

    // Print the statistics from graphwalker
    String actualResult = modelhandler.getStatistics();
    System.out.println(actualResult);
  }
/*
  @Test
  public void random() throws InterruptedException, StopConditionException, URISyntaxException {
    ModelHandler modelhandler = new ModelHandler();

    File file = new File("d://mbt/mod.graphml");
    Assert.assertNotNull(file,"File Not accessible");
    System.out.println(file.getName());


    // Connect the model to a java class, and add it to graphwalker's modelhandler.
    // The model is to be executed using the following criteria:
    // EFSM: Extended finite state machine is set to true, which means we are using the data domain
    // in the model
    // Generator: random, walk through the model randomly
    // Stop condition: Let the sequence be 20 steps long (pairs of edges and vertices)
    modelhandler.add("Game", new AcessAndy(file, true, new RandomPathGenerator(new EdgeCoverage(1.0)), false));

    // Start executing the test
    modelhandler.execute("Game");

    // Verify that the execution is complete, fulfilling the criteria from above.
    Assert.assertTrue(modelhandler.isAllModelsDone(), "Not all models are done");

    // Print the statistics from graphwalker
    String actualResult = modelhandler.getStatistics();
    System.out.println(actualResult);
  }

  @Test
  public void shoppingcart() throws InterruptedException, StopConditionException, URISyntaxException {
    ModelHandler modelhandler = new ModelHandler();

    // Get the model from resources
    URL url = MultiModelTest.class.getResource("/model/ShoppingCart.graphml");
    File file = new File(url.toURI());

    // Connect the model to a java class, and add it to graphwalker's modelhandler.
    // The model is to be executed using the following criteria:
    // Go the fastest path to the vertex v_ShoppingCart.
    modelhandler.add("Amazon", new Amazon(file, false, new A_StarPathGenerator(new ReachedVertex("v_ShoppingCart")), false));

    // Start executing the test
    modelhandler.execute("Amazon");

    // Verify that the execution is complete, fulfilling the criteria from above.
    Assert.assertTrue(modelhandler.isAllModelsDone(), "Not all models are done");

    // Print the statistics from graphwalker
    String actualResult = modelhandler.getStatistics();
    System.out.println(actualResult);
  }*/


}