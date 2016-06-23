package loongplugin.uml.action;

import java.util.List;







import loongplugin.uml.LoongUMLPlugin;
import loongplugin.uml.model.AbstractUMLEntityModel;
import loongplugin.uml.model.AbstractUMLModel;
import loongplugin.uml.model.AttributeModel;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;

public class AddAttributeAction extends AbstractTypeAction {
	
	public AddAttributeAction(CommandStack stack,  GraphicalViewer viewer){
		super(LoongUMLPlugin.getDefault().getResourceString("menu.addAttribute"), stack, viewer);
	}
	
	public void run(){
		stack.execute(new AddAttributeCommand(target));
	}
	
	private static class AddAttributeCommand extends Command {
		
		private AttributeModel attr;
		private AbstractUMLEntityModel target;
		
		public AddAttributeCommand(AbstractUMLEntityModel target){
			this.target = target;
		}
		
		public void execute(){
			attr = new AttributeModel();
			int count = 1;
			List<AbstractUMLModel> list = target.getChildren();
			for(int i=0;i<list.size();i++){
				if(list.get(i) instanceof AttributeModel){
					count++;
				}
			}
			attr.setName("attr" + count);
			target.copyPresentation(attr);
			target.addChild(attr);
		}
		
		public void undo() {
			target.removeChild(attr);
		}
	}
	
}

