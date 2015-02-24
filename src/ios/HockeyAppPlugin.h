#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>

@interface HockeyAppPlugin : CDVPlugin {
    BOOL initialized;
}

- (void)start:(CDVInvokedUrlCommand*)command;
- (void)feedback:(CDVInvokedUrlCommand*)command;

@end
