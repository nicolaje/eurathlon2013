/*
 * @(#)		main.cpp
 * @version	1.1
 * @autor	C. Rouvière
 */

 /**
 * Programme d'odométrie visuelle
 *	- exploite les librairies d'OpenCV pour traiter des images ...
 */

#include "Gui.h"
#include "Flux_cam.h"
#include "Tracking.h"

using namespace std;

int main(){

	Flux_cam flux(-1, 40, CV_RGB2GRAY, 3);	// initialisation du flux webcam (/dev/video0)
	Tracking tracking(100, 1000);		// classe contenant les infos de tracking
	Gui gui;				// IHM

	// boucle d'exécution : appuyer sur 'q' pour quitter
	while(flux.Get_key() != 'q'){
		// mettre à jour les images du flux
		flux.Update();
		tracking.Set_img_prev(flux.Get_prev());
		tracking.Set_img_next(flux.Get_next());
		// appliquer l'algorithme Lukas Kanade
		tracking.LK();
		// afficher le résultat
		gui.Ajouter_vecteurs("LK", flux.Get_cam(), tracking.Get_pts_prev(), tracking.Get_pts_next(), tracking.Get_pts_state());
		gui.Pad("Test", tracking.Get_moy_vect_x(), tracking.Get_moy_vect_y(), 50, 50);
	}

	return 0;

}
