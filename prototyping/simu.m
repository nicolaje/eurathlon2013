clear all; close all; clc;

%% Paramètres de la simulations

% Déplacement du robot
move=[[17 14];[24 17];[42 42]];
detectionAngles=[0 90 -90 180 45 135]; %Angle of detection
bruit=10; %erreur de mesure des capteurs télémétriques
% Obstacles en présence
obs=randi(60,5,2);
%% Génération des objets de base

A=zeros(60,60); %map.
%B=zeros(60,60); %obstacle probability.
C=zeros(60,60); %obstacle detection.
angles=detectionAngles*pi/180;
for k=1:1:length(obs)
    A(obs(k,1),obs(k,2))=1; %Obstacle
    %for x=max(obs(k,1)-5,1):1:min(obs(k,1)+5,60)
    %    for y=max(obs(k,2)-5,1):1:min(obs(k,2)+5,60)
    %        B(x,y)=B(x,y)+6*exp(-(obs(k,1)-x)^2-(obs(k,2)-y)^2); %Obstacle density
    %    end
    %end
    for x=max(obs(k,1)-1,1):1:min(obs(k,1)+1,60) %Obstacle detection range (due to sensors accuracy being not perfect)
        for y=max(obs(k,2)-1,1):1:min(obs(k,2)+1,60)
            C(x,y)=1;
        end
    end
end
% figure;
% imagesc(B);
% title('Obstacle density');
% figure;
% imagesc(A);
% title('Obstacles');
% figure;
% imagesc(C);
% title('Obstacle hitbox');

%% Simulation
figure;
proba=zeros(60);
for k=1:1:length(move)
    proba=zeros(60);
    subplot(length(move),2,2*k-1);
    pos=move(k,:);
    A(pos(1,1),pos(1,2))=5; %Position of the robot in the map
    imagesc(A);
    titl=strcat(num2str(pos(1,1)), num2str(pos(1,2)));
    title(titl);
    detec=detection(pos, C ,angles,bruit); %Detection of what the sensors should see at this position
    %Reconstitution of the probability of position
    xx=0;
    yy=0;
    bestpos=[0 0];
    maxproba=-10;
    for x=1:1:60 %look at all the map
        for y=1:1:60 
%             for w=1:1:length(detec) %for each detection
%                 det=detec(1,w);
%                 angle=angles(1,w);
%                 xx=floor(x+det*cos(angle));
%                 yy=floor(y+det*sin(angle));
%                 if(xx<=60 && xx>=1 && yy<60 && yy>=1) %if the cos(angle)*range detected is still in the map
%                     proba(x,y)=proba(x,y)+B(xx,yy);
%                 end
%             end
            det=detection([x y], C, angles, bruit);
            proba(x,y)=300/(1+norm(det-detec));
            if(proba(x,y)>maxproba)
                bestpos=[x,y];
                maxproba=proba(x,y);
            end
        end
    end
    subplot(length(move),2,2*k);
    imagesc(proba);
    ecart=sqrt((bestpos(1,1)-pos(1,1))^2+(bestpos(1,2)-pos(1,2))^2);
    ecstr= strcat('erreur: ',num2str(ecart), ' pos ', num2str(bestpos(1,1)), num2str(bestpos(1,2)));
    title(ecstr);
    A(pos(1,1),pos(1,2))=0;
end

%% Remarques

% Cet algorithme fonctionne très bien tant que le robot n'est pas "collé" à
% un obstacle