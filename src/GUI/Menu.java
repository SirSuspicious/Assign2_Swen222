package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolTip;

import main.Cluedo;

public class Menu extends JMenuBar {
	
	public Menu(){
		super();
		
		JMenu menu = new JMenu("Game"){
			public JToolTip createToolTip() {
				JToolTip t = new JToolTip(){
					public String getTipText() {
						return "Alt + G";
					}

				};
				t.setComponent(this);
				return t;
			}
		};

		menu.setMnemonic(KeyEvent.VK_G);
		
		menu.setToolTipText("q");
		
		JMenuItem ngItem = new JMenuItem("New Game"){
			public JToolTip createToolTip() {
			JToolTip t = new JToolTip(){
				public String getTipText() {
					return "Alt + N";
				}

			};
			t.setComponent(this);
			return t;
		}
		};
		ngItem.setToolTipText("l");
		
		
		ngItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Cluedo.newGame();
			}
		});
		ngItem.setMnemonic(KeyEvent.VK_N);
		menu.add(ngItem);

		ngItem = new JMenuItem("Exit"){
			
				public JToolTip createToolTip() {
					JToolTip t = new JToolTip(){
						public String getTipText() {
							return "Alt + E";
						}

					};
					t.setComponent(this);
					return t;
				}
			};
		ngItem.setMnemonic(KeyEvent.VK_E);
		ngItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		ngItem.setToolTipText("l");
		
		
		
		menu.add(ngItem);
		
		this.add(menu);

	}
	
}
