package loongplugin.color.coloredfile;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ChildPropertyDescriptor;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class ASTColorInheritance {
	/**
	 * returns if n inherits the colors of its parent node. usually colors are
	 * inherited, but there are some exceptions, i.e., if statements
	 */
	public static boolean inheritsColors(ASTNode parent, ASTNode n) {
		if (notInheritedProperties.contains(n.getLocationInParent()))
			return false;
		return true;
	}

	/**
	 * returns a nonempty list or null
	 * 
	 * @return
	 */
	// public static List<ASTNode> getNotInheritedChildren(ASTNode parent){
	// // to not inherit to blocks beneath if statements
	// if (parent instanceof IfStatement)
	// return wrapItems(((IfStatement) parent).getThenStatement(),((IfStatement)
	// parent).getElseStatement());
	// // same for for, while, do
	// if (parent instanceof WhileStatement)
	// return wrapItem(((WhileStatement) parent).getBody());
	// if (parent instanceof ForStatement)
	// return wrapItem(((ForStatement) parent).getBody());
	// if (parent instanceof DoStatement)
	// return wrapItem(((DoStatement) parent).getBody());
	// // same for try
	// if (parent instanceof TryStatement)
	// return wrapItem(((TryStatement) parent).getBody());
	// return null;
	// }
	public static final Set<ChildPropertyDescriptor> notInheritedProperties;
	static {
		notInheritedProperties = new HashSet<ChildPropertyDescriptor>();
		notInheritedProperties.add(IfStatement.THEN_STATEMENT_PROPERTY);
		notInheritedProperties.add(IfStatement.ELSE_STATEMENT_PROPERTY);
		notInheritedProperties.add(WhileStatement.BODY_PROPERTY);
		notInheritedProperties.add(ForStatement.BODY_PROPERTY);
		notInheritedProperties.add(DoStatement.BODY_PROPERTY);
		notInheritedProperties.add(TryStatement.BODY_PROPERTY);
		notInheritedProperties.add(SynchronizedStatement.BODY_PROPERTY);
		notInheritedProperties.add(ConditionalExpression.THEN_EXPRESSION_PROPERTY);
		notInheritedProperties.add(ConditionalExpression.ELSE_EXPRESSION_PROPERTY);
	}

	// private static List<ASTNode> wrapItem(ASTNode node){
	// List<ASTNode> result=new ArrayList<ASTNode>();
	// if (node!=null)
	// result.add(node);
	// return result;
	// }
	// private static List<ASTNode> wrapItems(ASTNode nodeA,ASTNode nodeB){
	// List<ASTNode> result=new ArrayList<ASTNode>();
	// if (nodeA!=null)
	// result.add(nodeA);
	// if (nodeB!=null)
	// result.add(nodeB);
	// return result;
	// }
}
