package maintest;

import lesschtroumpfs.Schtroumpf;

public class MainSchtroumpf {

	public static void main (String[] argv) {
		Schtroumpf personnageSchtroumpf = new Schtroumpf("grognon", 135);
		personnageSchtroumpf.sePresenter();
		personnageSchtroumpf.chanter();
		personnageSchtroumpf.allerTravailler();
		personnageSchtroumpf.sePresenter();
		personnageSchtroumpf.chanter();
		personnageSchtroumpf.manger(10);
		personnageSchtroumpf.sePresenter();
		personnageSchtroumpf.chanter();
		personnageSchtroumpf.anniversaire();
		personnageSchtroumpf.sePresenter();
		personnageSchtroumpf.chanter();

	}

}
