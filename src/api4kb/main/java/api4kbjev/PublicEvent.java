package api4kbjev;

import api4kbj.Event;
import api4kbjpro.Proficiency;

public interface PublicEvent extends Event {

	boolean actualizes(Proficiency proficiency);

}
