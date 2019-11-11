docker pull datagrip/derby-server:10.12
# Stworzenie tutaj woluminu pod Windowsem robi dziwne problemiki przy próbie podłączenia do bazy z create=true.
docker run --rm -d -p 1527:1527 datagrip/derby-server:10.12