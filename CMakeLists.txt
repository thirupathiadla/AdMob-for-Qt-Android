cmake_minimum_required(VERSION 3.16)

project(admobmanager VERSION 0.1 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(Qt6 6.5 REQUIRED COMPONENTS Quick Widgets Core)

qt_policy(SET QTP0002 NEW)

qt_standard_project_setup(REQUIRES 6.5)

qt_add_executable(appadmobmanager
    MANUAL_FINALIZATION
    main.cpp
    android/AndroidManifest.xml
    android/src/com/AdEventHandlers.java
    admobbridge.h admobbridge.cpp
    android/src/mock-google-services.json
)

#qt_add_qml_module(apptesting
 #   URI testing
  #  VERSION 1.0
   # QML_FILES
    #    Main.qml
     #   RESOURCES android/AndroidManifest.xml android/build.gradle android/res/values/libs.xml android/res/xml/qtprovider_paths.xml android/gradle/wrapper/gradle-wrapper.jar android/gradle/wrapper/gradle-wrapper.properties android/gradle.properties android/gradlew android/gradlew.bat
#)

# Qt for iOS sets MACOSX_BUNDLE_GUI_IDENTIFIER automatically since Qt 6.1.
# If you are developing for iOS or macOS you should consider setting an
# explicit, fixed bundle identifier manually though.
set_target_properties(appadmobmanager PROPERTIES
#    MACOSX_BUNDLE_GUI_IDENTIFIER com.example.apptesting
    MACOSX_BUNDLE_BUNDLE_VERSION ${PROJECT_VERSION}
    MACOSX_BUNDLE_SHORT_VERSION_STRING ${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}
    MACOSX_BUNDLE TRUE
    WIN32_EXECUTABLE TRUE
)

target_link_libraries(appadmobmanager
    PRIVATE Qt6::Quick
    PRIVATE Qt6::Core
    PRIVATE Qt6::Widgets
)

set_property(TARGET appadmobmanager APPEND PROPERTY QT_ANDROID_PACKAGE_SOURCE_DIR
    ${CMAKE_CURRENT_SOURCE_DIR}/android)

qt_finalize_executable(appadmobmanager)

#qt_add_resources(apptesting "main"
   # PREFIX
   #     "/"
    #FILES
    #${qml_resource_files}
#)

include(GNUInstallDirs)
install(TARGETS appadmobmanager
    BUNDLE DESTINATION .
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    RUNTIME DESTINATION ${CMAKE_INSTALL_BINDIR}
)

qt_generate_deploy_app_script(
    TARGET appadmobmanager
    OUTPUT_SCRIPT deploy_script
    NO_UNSUPPORTED_PLATFORM_ERROR
)
install(SCRIPT ${deploy_script})
