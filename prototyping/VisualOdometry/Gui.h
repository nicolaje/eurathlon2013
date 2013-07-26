/*
 * @(#)		Gui.h
 * @version	1.1
 * @autor	C. Rouvière
 */

/**
 * Classe affichant une image OpenCV2 dans une fenêtre
 *	- peut également y ajouter des vecteurs
 */

#ifndef GUI
#define GUI

#include <opencv/cv.h>
#include <opencv/highgui.h>

class Gui{

public:

	Gui();

	void Afficher_image(const std::string titre_fenetre, cv::Mat image);
	void Pad(const std::string titre_fenetre, const int dx, const int dy, const int dx_max, const int dy_max);
	void Ajouter_vecteurs(
		const std::string titre_fenetre,	// titre de la fenêtre
		cv::Mat image,				// image à traiter
		std::vector<cv::Point2f> pts_prev,	// tableau des points initiaux
		std::vector<cv::Point2f> pts_next,	// tableau des points finals
		std::vector<uchar> pts_ok);		// tableau de validation

private:

	#define TAILLE_POD 200

	cv::Scalar rouge;
	cv::Size dim_1;
	cv::Point pod_centre;

};

#endif
