#include "Tracking.h"

using namespace std;

// Constructeurs
Tracking::Tracking(){Init(42, 200);}
Tracking::Tracking(const int nb_max_amers, const int ecart_max){
	Init(nb_max_amers, ecart_max);
}

// Initialisation complète de la classe (automatique à la construction)
void Tracking::Init(const int nb_max_amers, const int ecart_max){
	this->nb_max_amers = nb_max_amers;
	this->ecart_max = ecart_max;
	critere = cvTermCriteria(CV_TERMCRIT_ITER | CV_TERMCRIT_EPS, 50, 0.03);
}

// Appeler l'algorithme de Lucas Kanade
void Tracking::LK(){
	vector<float> err;
	goodFeaturesToTrack(img_prev, amers, nb_max_amers, 0.01, 10);
	cornerSubPix(img_prev, amers, cv::Size(5, 5), cv::Size(-1, -1), critere);
	calcOpticalFlowPyrLK(img_prev, img_next, amers, nv, status, err);
	Filtrer();
	Calculer_deplacement_moyen();
}

// Supprimer les vecteurs trop grands
void Tracking::Filtrer(){
	for(size_t i = 0; i < amers.size(); i++){
		if(Distance_carree(amers[i].x, amers[i].y, nv[i].x, nv[i].y) > ecart_max){status[i] = 0;}
	}
}

// Temporaire : calcule la moyenne de déplacement
void Tracking::Calculer_deplacement_moyen(){
	int somme_x = 0, somme_y = 0, nb_vect = 0; moy_vect_x = 0; moy_vect_y = 0;
	for(size_t i = 0; i < amers.size(); i++){
		if(status[i] == 1){somme_x += nv[i].x - amers[i].x; somme_y += nv[i].y - amers[i].y; nb_vect++;}
	}
	if(nb_vect > 0){moy_vect_x = somme_x / nb_vect;	moy_vect_y = somme_y / nb_vect;}
}

// Calculer une distance au carré pour la norme 2
int Tracking::Distance_carree(int x1, int y1, int x2, int y2){
	int dx = x2 - x1, dy = y2 - y1;
	return dx*dx + dy*dy;
}

// Getters
vector<cv::Point2f> Tracking::Get_pts_prev() const{return amers;}
vector<cv::Point2f> Tracking::Get_pts_next() const{return nv;}
vector<uchar> Tracking::Get_pts_state() const{return status;}
int Tracking::Get_moy_vect_x() const{return moy_vect_x;}
int Tracking::Get_moy_vect_y() const{return moy_vect_y;}

// Setters
void Tracking::Set_img_prev(cv::Mat image){image.copyTo(img_prev);}
void Tracking::Set_img_next(cv::Mat image){image.copyTo(img_next);}
