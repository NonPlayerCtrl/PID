package Energy.hmi;

import java.awt.event.ActionEvent;

public class EnergyMask extends EnergyMask_generated {
	private static final long serialVersionUID = 1L;
	public EnergyMask() throws Exception {
		super();
		//Reaction on Component actions like pressed button
		//Todo: register component to react here
		//<component>.addActionListener(this);
	}
	/**
	 * This method reacts on action commands 
	 */
	public void actionPerformed(ActionEvent ev) {
		super.actionPerformed(ev);
		//if (ev.getSource() == <component>) {
		//Todo: add action here
		//}
	}
}
