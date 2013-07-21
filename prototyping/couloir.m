
%% Paramètres de la simulations

% Déplacement du robot
move=[[103 15]];%[24 17];[42 42]];
detectionAngles=[0 90 -90 180]; %Angle of detection
bruit=2; %erreur de mesure des capteurs télémétriques

%% Création de la carte

mapp=zeros(500, 30);
for k=0:1:90 %Premier triangle
    for l=1:1:10-floor(abs(10*(45-k)/30))
        mapp(k,l)=1;
    end
end

for k=400:1:440 %Second triangle
    for l=1:1:10-floor(abs(10*(420-k)/20))
        mapp(k,31-l)=1;
    end
end

for k=160:1:200 %First square
    for l=16:1:30
        mapp(k,l)=1;
    end
end

for k=220:1:226 %door
    for l=1:1:16
        mapp(k,l)=1;
    end
    for l=1:1:10
        mapp(k,31-l)=1;
    end
end

for k=440:1:446 %door
    for l=1:1:16
        mapp(k,l)=1;
    end
    for l=1:1:10
        mapp(k,31-l)=1;
    end
end

for k=340:1:346 %door
    for l=1:1:16
        mapp(k,31-l)=1;
    end
    for l=1:1:10
        mapp(k,l)=1;
    end
end

mapp(150,6)=1; %un poteau

for k=60:1:78 %demi cercle
    for l=1:1:ceil(sqrt(81-(k-69)^2))
        mapp(k,31-l)=1;
    end
end
imagesc(mapp);
axis equal;
%% Simulation
figure;
bounds=size(mapp);
angles=detectionAngles*pi/180;
len=size(move);
tic;
for k=1:1:len(1,1)
    proba=zeros(bounds(1,1),bounds(1,2));
    subplot(len(1,1),2,2*k-1);
    pos=move(k,:);
    mapp(pos(1,1),pos(1,2))=5; %Position of the robot in the map
    imagesc(mapp);
    mapp(pos(1,1),pos(1,2))=0;
    titl=strcat(num2str(pos(1,1)), num2str(pos(1,2)));
    title(titl);
    detec=detection(pos, mapp ,angles,bruit); %Detection of what the sensors should see at this position
    %Reconstitution of the probability of position
    xx=0;
    yy=0;
    bestpos=[0 0];
    maxproba=-10;
    for x=1:1:bounds(1,1) %look at all the map
        for y=1:1:bounds(1,2); 
            
            if(mapp(x,y)==0)
%             for w=1:1:length(detec) %for each detection
%                 det=detec(1,w);
%                 angle=angles(1,w);
%                 xx=floor(x+det*cos(angle));
%                 yy=floor(y+det*sin(angle));
%                 if(xx<=60 && xx>=1 && yy<60 && yy>=1) %if the cos(angle)*range detected is still in the map
%                     proba(x,y)=proba(x,y)+B(xx,yy);
%                 end
%             end
                det=detection([x y], mapp, angles, bruit);
                proba(x,y)=1/(1+norm(det-detec));
                if(proba(x,y)>maxproba)
                    bestpos=[x,y];
                    maxproba=proba(x,y);
                end
            end
        end
    end
    time=toc;
    display(time)
    subplot(len(1,1),2,2*k);
    imagesc(proba);
    ecart=sqrt((bestpos(1,1)-pos(1,1))^2+(bestpos(1,2)-pos(1,2))^2);
    ecstr= strcat('erreur: ',num2str(ecart), ' pos ', num2str(bestpos(1,1)), num2str(bestpos(1,2)));
    title(ecstr);
    
end
