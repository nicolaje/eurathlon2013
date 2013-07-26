/*
 * @(#)		Tracking.h
 * @version	1.1
 * @autor	C. Rouvière
 */

/**
 * Classe calculant le mouvement entre deux images consécutives
 *	- on doit y entrer les deux images via des setters
 *	- on peut appliquer l'algorithme de Lukas Kanade via "void LK()"
 *	- on récupère les vecteurs via des getters
 *	- si un vecteur est invalide, le "uchar status" correspondant vaut 0
 *	- attention : les images doivent être au format CV_8UC1 ou CV_32FC1
 */

#ifndef TRACKING
#define TRACKING

#include <opencv/cv.h>
#include <opencv/highgui.h>

class Tracking{

public:

	Tracking();
	Tracking(const int nb_max_amers, const int ecart_max);

	void Set_img_prev(cv::Mat img_prev);		// getter image N-1
	void Set_img_next(cv::Mat img_next);		// getter image N
	std::vector<cv::Point2f> Get_pts_prev() const;	// getter amers
	std::vector<cv::Point2f> Get_pts_next() const;	// getter nv
	std::vector<uchar> Get_pts_state() const;	// getter status
	int Get_moy_vect_x() const;			// getter deplacement moyen selon x
	int Get_moy_vect_y() const;			// getter deplacement moyen selon y
	void LK();					// Lukas Kanade

private:

	int nb_max_amers;		// nombre maximum de points à tracker
	int ecart_max;			// vitesse maximale d'un point au carré

	cv::Size fen_search;		// fenêtre de recherche de correspondance
	cv::TermCriteria critere;	// critère de recherche
	cv::Mat img_prev;		// image N-1
	cv::Mat img_next;		// image N
	std::vector<cv::Point2f> amers;	// points à repérer N-1
	std::vector<cv::Point2f> nv;	// points à repérer N
	std::vector<uchar> status;	// si les points ont été repérés
	int moy_vect_x, moy_vect_y;	// déplacement moyen selon x et y

	void Init(const int nb_max_amers, const int ecart_max);
	void Filtrer();
	void Calculer_deplacement_moyen();

	int Distance_carree(int x1, int y1, int x2, int y2);

};

#endif
