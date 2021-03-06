import org.junit.Test;
import org.junit.After;

import vehicle.*;
import core.*;
public class InfotainmentTest {
    
   @Test
   public void NetworkAccessFromInfotainmentTest() {
      /*
      This test case models an attack from the infotainment system which has a network access service (which might be stopped by default)

       ---> Account 
       |       |
       |  Infotainment <---> VehicleNetwork
       |       |
      NetworkAccessService

      */
      System.out.println("### " + Thread.currentThread().getStackTrace()[1].getMethodName());
      // Start of test
      InfotainmentSystem infosys = new InfotainmentSystem("InfoSys");
      VehicleNetwork vNet = new VehicleNetwork("vNet");
      NetworkAccessService netSrv = new NetworkAccessService("NetService");
      Account account = new Account("Account");

      infosys.addConnectedNetworks(vNet);
      infosys.addExecutees(netSrv);
      infosys.addAccount(account);
      netSrv.addAccount(account);
      
      Attacker atk = new Attacker();
      atk.addAttackPoint(infosys.connect);
      atk.addAttackPoint(account.compromise);
      atk.attack();
      
      infosys.access.assertCompromisedInstantaneously();
      netSrv.access.assertCompromisedInstantaneously();
      infosys.gainNetworkAccess.assertCompromisedInstantaneously();

      vNet.accessNetworkLayer.assertCompromisedInstantaneously();
    }

    @Test
   public void EngineerNetworkAccessFromInfotainmentTest() {
      /*
      This test case models an attack from the infotainment system which has not a network access service so the attacker must engineer it!

         Account 
            |
      Infotainment <---> VehicleNetwork

      */
      System.out.println("### " + Thread.currentThread().getStackTrace()[1].getMethodName());
      // Start of test
      InfotainmentSystem infosys = new InfotainmentSystem("InfoSys");
      VehicleNetwork vNet = new VehicleNetwork("vNet");
      Account account = new Account("Account");

      infosys.addConnectedNetworks(vNet);
      infosys.addAccount(account);
      
      Attacker atk = new Attacker();
      atk.addAttackPoint(infosys.connect);
      atk.addAttackPoint(account.compromise);
      atk.attack();
      
      infosys.access.assertCompromisedInstantaneously();
      infosys.gainNetworkAccess.assertUncompromised();
      infosys.engineerNetworkAccess.assertCompromisedWithEffort();
      vNet.accessNetworkLayer.assertCompromisedWithEffort();
    }
   
    @After
    public void deleteModel() {
            Asset.allAssets.clear();
            AttackStep.allAttackSteps.clear();
            Defense.allDefenses.clear();
    }
    
}
