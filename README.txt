Rendu du TP: Intelligence Artificielle


Partie 1 : Modelling et Planning

Membres du groupe:
Adouma Hassan brahim: 21901741
KOTIN Melas: 22211719

Compilation:
    - cd src
    - find -name  "*.java" > sources.txt
    - javac -d ../build -cp ".:../lib/modellingtests.jar:../lib/planningtests.jar:../lib/cptests.jar:../lib/dataminingtests.jar" @sources.txt


Exectuion:
    - java  -cp "../build:../lib/modellingtests.jar:../lib/planningtests.jar:../lib/cptests.jar:../lib/dataminingtests.jar" modelling.Test
    - java  -cp "../build:../lib/modellingtests.jar:../lib/planningtests.jar:../lib/cptests.jar:../lib/dataminingtests.jar" planning.Test
    - java  -cp "../build:../lib/modellingtests.jar:../lib/planningtests.jar:../lib/cptests.jar:../lib/dataminingtests.jar" datamining.Test
    - java  -cp "../build:../lib/modellingtests.jar:../lib/planningtests.jar:../lib/cptests.jar:../lib/dataminingtests.jar" cp.Test