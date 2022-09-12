// IHandlerService.aidl
package xyz.mufanc.macro;

// Declare any non-default types here with import statements

interface IHandlerService {
    oneway void onNewIntent(in Bundle bundle);
}
