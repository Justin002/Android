e:
cd E:\gitHub\ep2e\ep2p-app
cd library
call android update lib-project -t 3 -p ./
call ant clean
cd ..
cd ep2p_app
call android update project --name ep2p_app -t 3 -p ./ --subprojects
call ant deployRelease
pause