
cd /home/estefano/FamilyGroup/
#derruba possíveis containers em execução
docker-compose down

#build api image
docker build -t familygroup:latest ./familygroup

#start environment
docker-compose -f /home/estefano/FamilyGroup/docker-compose.yml up --build --force-recreate --remove-orphans

