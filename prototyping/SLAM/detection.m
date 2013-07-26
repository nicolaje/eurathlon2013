function res=detection(pos,C,detectionAngles,bruit)
    bounds=size(C);
    res=zeros(size(detectionAngles));
    for p=1:1:length(detectionAngles)
       curseur=[0 0];
       found=false;
       angle=detectionAngles(1,p);
       pointeur=[pos(1,1)+floor(curseur(1,1)),pos(1,2)+floor(curseur(1,2))];
       while(found==false && pointeur(1,1)<=bounds(1,1) && pointeur(1,1)>=1 && pointeur(1,2)<=bounds(1,2) && pointeur(1,2)>=1)
           if(C(pos(1,1)+floor(curseur(1,1)),pos(1,2)+floor(curseur(1,2)))>0)
               found=true;
           end
           curseur=[curseur(1,1)+cos(angle) curseur(1,2)+sin(angle)];
           pointeur=[pos(1,1)+floor(curseur(1,1)),pos(1,2)+floor(curseur(1,2))];
       end
       res(1,p)=sqrt(curseur(1,1)^2+curseur(1,2)^2)+bruit*rand;
    end


end