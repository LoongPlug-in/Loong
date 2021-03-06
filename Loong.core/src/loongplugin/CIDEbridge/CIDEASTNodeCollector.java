package loongplugin.CIDEbridge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.Dimension;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.IntersectionType;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NameQualifiedType;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodReference;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeMethodReference;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;

public class CIDEASTNodeCollector extends ASTVisitor{

	private Set<ASTNode> associateNodes;
	private Map<IBinding, Set<ASTNode>> associatebindingastnodes;
	
	public CIDEASTNodeCollector() {
		// TODO Auto-generated constructor stub
		associateNodes = new HashSet<ASTNode>();
		associatebindingastnodes = new HashMap<IBinding, Set<ASTNode>>();
	}
	public Set<ASTNode> getASTNodeSet(){
		return associateNodes;
	}
	public Map<IBinding,Set<ASTNode>> getBindingASTNodeSet(){
		return associatebindingastnodes;
	}

	@Override
	public void preVisit(ASTNode node) {
		super.preVisit(node);
		associateNodes.add(node);
	}
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(ArrayAccess node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(ArrayCreation node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(ArrayInitializer node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(ArrayType node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(AssertStatement node) {
		// TODO Auto-generated method stub
		
		return super.visit(node);
	}
	@Override
	public boolean visit(Assignment node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(Block node) {
		// TODO Auto-generated method stub
		
		return super.visit(node);
	}
	@Override
	public boolean visit(BlockComment node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	@Override
	public boolean visit(BooleanLiteral node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(BreakStatement node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	@Override
	public boolean visit(CastExpression node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(CatchClause node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	@Override
	public boolean visit(CharacterLiteral node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	@Override
	public boolean visit(ClassInstanceCreation node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveConstructorBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(ConstructorInvocation node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveConstructorBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(CreationReference node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveMethodBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(Dimension node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveConstructorBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		IBinding varbinding = node.resolveVariable();
		if(associatebindingastnodes.containsKey(varbinding)){
			associatebindingastnodes.get(varbinding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(varbinding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(EnumDeclaration node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(ExpressionMethodReference node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveMethodBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		IBinding typebinding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(typebinding)){
			associatebindingastnodes.get(typebinding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(typebinding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(FieldAccess node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveFieldBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		IBinding typebinding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(typebinding)){
			associatebindingastnodes.get(typebinding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(typebinding, nodeset);
		}
		return super.visit(node);
	}
	
	
	@Override
	public boolean visit(ImportDeclaration node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(InfixExpression node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(InstanceofExpression node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(MethodRef node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(MethodInvocation node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveMethodBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		IBinding typebinding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(typebinding)){
			associatebindingastnodes.get(typebinding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(typebinding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(NameQualifiedType node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(NormalAnnotation node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	@Override
	public boolean visit(NullLiteral node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	@Override
	public boolean visit(NumberLiteral node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	@Override
	public boolean visit(PackageDeclaration node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	@Override
	public boolean visit(ParameterizedType node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(ParenthesizedExpression node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(PostfixExpression node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(PrefixExpression node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(QualifiedName node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(QualifiedType node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(ReturnStatement node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	@Override
	public boolean visit(SimpleName node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		IBinding typebinding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(typebinding)){
			associatebindingastnodes.get(typebinding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(typebinding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(SimpleType node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(SingleVariableDeclaration node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(SuperConstructorInvocation node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveConstructorBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(SuperFieldAccess node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveFieldBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		IBinding typebinding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(typebinding)){
			associatebindingastnodes.get(typebinding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(typebinding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(SuperMethodInvocation node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveMethodBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		IBinding typebinding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(typebinding)){
			associatebindingastnodes.get(typebinding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(typebinding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(SuperMethodReference node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveMethodBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		IBinding typebinding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(typebinding)){
			associatebindingastnodes.get(typebinding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(typebinding, nodeset);
		}
		return super.visit(node);
	}
	
	
	@Override
	public boolean visit(ThisExpression node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		
		return super.visit(node);
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(TypeDeclarationStatement node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(TypeLiteral node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(TypeMethodReference node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveMethodBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		IBinding typebinding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(typebinding)){
			associatebindingastnodes.get(typebinding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(typebinding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(TypeParameter node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(UnionType node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	@Override
	public boolean visit(VariableDeclarationExpression node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveTypeBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		
		
		return super.visit(node);
	}
	
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(WildcardType node) {
		// TODO Auto-generated method stub
		IBinding binding = node.resolveBinding();
		if(associatebindingastnodes.containsKey(binding)){
			associatebindingastnodes.get(binding).add(node);
		}else{
			Set<ASTNode>nodeset = new HashSet<ASTNode>();
			nodeset.add(node);
			associatebindingastnodes.put(binding, nodeset);
		}
		return super.visit(node);
	}
	
	
	
	
}
