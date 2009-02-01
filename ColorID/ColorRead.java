/**
 * @(#)ColorRead.java
 *
 *
 * @author 
 * @version 1.00 2009/2/1
 */
 
 
/**
 * Copyright 2003 Sun Microsystems, Inc.
 * 
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL 
 * WARRANTIES.
 */
 
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.JavaClipAudioPlayer;

public class ColorRead 
{
	Voice talkingHead;					// The voice that will do the talking
	VoiceManager voiceManager;			// Voice manager, not really sure what it does
	static final String DEFAULT_VOICE = "kevin16";
	
    public ColorRead(String voiceName) 
    {
    	// Set the initial voice
    	voiceManager = VoiceManager.getInstance();
        talkingHead = voiceManager.getVoice(voiceName);
        
        // If we were not able to do it on the specified voice
        // output the warning and go to default
        if( talkingHead == null)
        {
        	talkingHead = voiceManager.getVoice(DEFAULT_VOICE);
        	System.out.println("WARNING:  voice \"" + voiceName + "\" not found, using default.");
        }
        
        // Allocate the voice resources
        talkingHead.allocate();        
    	
    }
    
    public void speak(String whatToSay)
    {
    	// Gets the speaker to say the correct text
    	talkingHead.speak(whatToSay);  	
    }
    
    public void changeVoice(String newVoiceName)
    {
    	// Deallocate the old voice resources
    	talkingHead.deallocate();
    	
    	// Change the voice
    	talkingHead = voiceManager.getVoice(newVoiceName);
    	
    	// If we were not able to do it on the specified voice
        // output the warning and go to default
        if( talkingHead == null)
        {
        	talkingHead = voiceManager.getVoice(DEFAULT_VOICE);
        	System.out.println("WARNING:  voice \"" + newVoiceName + "\" not found, using default.");
        }
    	
    	// Re-allocate the voice resources
    	talkingHead.allocate();
    }
    
    
}