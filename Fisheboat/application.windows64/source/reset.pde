void reset() {   // Fonction pour reset toutes les variables importantes

  if(ResetBool == true){
    xHmc = 0;
    yHmc = 0;
    xBoat = 150;
    yBoat = 411;
    EcartHmc = 50;
    xFond = -6800;
    score = 0;  
    DisplayFishTimer = 100;    
    j = 0;
    i =0;
    GreenFishCollider = false;
    YellowFishCollider = false;
    RedFishCollider = false;
    GoldFishCollider = false;
    GreenMouseReleased = false;
    YellowMouseReleased = false;
    RedMouseReleased = false;
    GoldMouseReleased = false;
    ComboGreenTimer = 45;
    ComboRedTimer = 45;
    ComboYellowTimer = 45;
    ComboGoldTimer = 45;
    ComboScoreTimer = 0;
    ComboScoreX2 = false;
    ComboScoreX3 = false;
 ComboScoreX4 = false;
 ComboScoreX5 = false;
 Score1 = false;
 Score2 = false;
 Score4 = false;
 Score8 = false;
 Scoremoins1 = false;
 CreateAllFishesBool = true;
 yBarre = 230;
    
    ResetBool = false;
    
    
  }
}
