#include <QApplication>
#include <QQmlApplicationEngine>
#include "admobbridge.h"

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);


    AdMobBridge admob;
    admob.setWindowTitle("Button Color Change");
    admob.resize(300,200);
    admob.show();

    /*
    QQmlApplicationEngine engine;
    QObject::connect(
        &engine,
        &QQmlApplicationEngine::objectCreationFailed,
        &app,
        []() { QCoreApplication::exit(-1); },
        Qt::QueuedConnection);
    engine.loadFromModule("testing", "Main");
    */

    return app.exec();
}
