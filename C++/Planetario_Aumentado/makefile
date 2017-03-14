# Ruta absoluta o relativa (cuidado con espacios en blanco al final del path!)
ARTOOLKITDIR = /home/miguel/ARToolKit

INC_DIR      = $(ARTOOLKITDIR)/include
LIB_DIR      = $(ARTOOLKITDIR)/lib

LIBS   = -lARgsub -lARvideo -lAR -lGL -lGLU -lglut

NAMEEXEC     = sistemasolar

all: $(NAMEEXEC)

$(NAMEEXEC): Main.cpp
	g++ -I $(INC_DIR) -o $(NAMEEXEC) *.cpp -L$(LIB_DIR) $(LIBS) -w
clean:
	rm -f *.o $(NAMEEXEC) *~ *.*~

