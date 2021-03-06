package loongplugin.views.recommendedfeatureview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import loongplugin.utils.Stemmer;
import loongplugin.utils.StringListToFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTNode;

public class FeatureNameDictionary {
	
	private final Map<String,Map<IJavaElement,Set<ASTNode>>>featureNameDictionary = new HashMap<String,Map<IJavaElement,Set<ASTNode>>>();
	private final Map<String,Map<IJavaElement,Set<ASTNode>>>nonfeaturetextMapping = new HashMap<String,Map<IJavaElement,Set<ASTNode>>>();
	private IProject project = null;
	private static FeatureNameDictionary instance;
	private IProgressMonitor monitor;
	private String tempfilePath = "input.txt";
	private List<String>allStringList = new LinkedList<String>();
	
	public static FeatureNameDictionary getInstance(IProgressMonitor pmonitor){
		if(instance==null)
			instance = new FeatureNameDictionary(pmonitor);
		return instance;
	}
	public FeatureNameDictionary(IProgressMonitor pmonitor){
		this.monitor = pmonitor;
	}
	
	public void addDictBuiltElement(String associatedString,IJavaElement element,ASTNode astnode){
		String asssociatedStringLowwercase = associatedString.toLowerCase();
		asssociatedStringLowwercase = replaceSymbolToSpace(asssociatedStringLowwercase);
		asssociatedStringLowwercase = asssociatedStringLowwercase.trim();
		Stemmer stemmer = new Stemmer();
		asssociatedStringLowwercase = stemmer.stem(asssociatedStringLowwercase);
		
		if(featureNameDictionary.containsKey(asssociatedStringLowwercase)){
			Map<IJavaElement,Set<ASTNode>> iJavaElementBindings = featureNameDictionary.get(asssociatedStringLowwercase);
			if(iJavaElementBindings.containsKey(element)){
				Set<ASTNode> bindingastnodes = iJavaElementBindings.get(element);
				bindingastnodes.add(astnode);
				iJavaElementBindings.put(element, bindingastnodes);
				featureNameDictionary.put(asssociatedStringLowwercase, iJavaElementBindings);
			}else{
				Set<ASTNode> bindingastnodes = new HashSet<ASTNode>();
				bindingastnodes.add(astnode);
				iJavaElementBindings.put(element, bindingastnodes);
				featureNameDictionary.put(asssociatedStringLowwercase, iJavaElementBindings);
			}
		}else{
			Map<IJavaElement,Set<ASTNode>> iJavaElementBindings = new HashMap<IJavaElement,Set<ASTNode>>();
			Set<ASTNode> bindingastnodes = new HashSet<ASTNode>();
			bindingastnodes.add(astnode);
			iJavaElementBindings.put(element, bindingastnodes);
			featureNameDictionary.put(asssociatedStringLowwercase, iJavaElementBindings);
		}
	}
	public void addDictBuiltElement(String associatedString,IJavaElement element,Set<ASTNode> astnodes){
		String asssociatedStringLowwercase = associatedString.toLowerCase();
		asssociatedStringLowwercase = replaceSymbolToSpace(asssociatedStringLowwercase);
		asssociatedStringLowwercase = asssociatedStringLowwercase.trim();
		Stemmer stemmer = new Stemmer();
		asssociatedStringLowwercase = stemmer.stem(asssociatedStringLowwercase);
		
		if(featureNameDictionary.containsKey(asssociatedStringLowwercase)){
			Map<IJavaElement,Set<ASTNode>> iJavaElementBindings = featureNameDictionary.get(asssociatedStringLowwercase);
			if(iJavaElementBindings.containsKey(element)){
				Set<ASTNode> bindingastnodes = iJavaElementBindings.get(element);
				bindingastnodes.addAll(astnodes);
				iJavaElementBindings.put(element, bindingastnodes);
				featureNameDictionary.put(asssociatedStringLowwercase, iJavaElementBindings);
			}else{
				Set<ASTNode> bindingastnodes = new HashSet<ASTNode>();
				bindingastnodes.addAll(astnodes);
				iJavaElementBindings.put(element, bindingastnodes);
				featureNameDictionary.put(asssociatedStringLowwercase, iJavaElementBindings);
			}
		}else{
			Map<IJavaElement,Set<ASTNode>> iJavaElementBindings = new HashMap<IJavaElement,Set<ASTNode>>();
			Set<ASTNode> bindingastnodes = new HashSet<ASTNode>();
			bindingastnodes.addAll(astnodes);
			iJavaElementBindings.put(element, bindingastnodes);
			featureNameDictionary.put(asssociatedStringLowwercase, iJavaElementBindings);
		}
	}
	
	
	public void addAnyElement(String associatedString,IJavaElement element,ASTNode astnode){
		String asssociatedStringLowwercase = associatedString.toLowerCase();
		asssociatedStringLowwercase = replaceSymbolToSpace(asssociatedStringLowwercase);
		asssociatedStringLowwercase = asssociatedStringLowwercase.trim();
		Stemmer stemmer = new Stemmer();
		asssociatedStringLowwercase = stemmer.stem(asssociatedStringLowwercase);
		if(nonfeaturetextMapping.containsKey(asssociatedStringLowwercase)){
			Map<IJavaElement,Set<ASTNode>> iJavaElementBindings = nonfeaturetextMapping.get(asssociatedStringLowwercase);
			if(iJavaElementBindings.containsKey(element)){
				Set<ASTNode> bindingastnodes = iJavaElementBindings.get(element);
				bindingastnodes.add(astnode);
				iJavaElementBindings.put(element, bindingastnodes);
				nonfeaturetextMapping.put(asssociatedStringLowwercase, iJavaElementBindings);
			}else{
				Set<ASTNode> bindingastnodes = new HashSet<ASTNode>();
				bindingastnodes.add(astnode);
				iJavaElementBindings.put(element, bindingastnodes);
				nonfeaturetextMapping.put(asssociatedStringLowwercase, iJavaElementBindings);
			}
		}else{
			Map<IJavaElement,Set<ASTNode>> iJavaElementBindings = new HashMap<IJavaElement,Set<ASTNode>>();
			Set<ASTNode> bindingastnodes = new HashSet<ASTNode>();
			bindingastnodes.add(astnode);
			iJavaElementBindings.put(element, bindingastnodes);
			nonfeaturetextMapping.put(asssociatedStringLowwercase, iJavaElementBindings);
		}
	}
	public void addAnyElement(String associatedString,IJavaElement element,Set<ASTNode> astnodes){
		String asssociatedStringLowwercase = associatedString.toLowerCase();
		
		asssociatedStringLowwercase = replaceSymbolToSpace(asssociatedStringLowwercase);
		asssociatedStringLowwercase = asssociatedStringLowwercase.trim();
		Stemmer stemmer = new Stemmer();
		asssociatedStringLowwercase = stemmer.stem(asssociatedStringLowwercase);
		if(nonfeaturetextMapping.containsKey(asssociatedStringLowwercase)){
			Map<IJavaElement,Set<ASTNode>> iJavaElementBindings = nonfeaturetextMapping.get(asssociatedStringLowwercase);
			if(iJavaElementBindings.containsKey(element)){
				Set<ASTNode> bindingastnodes = iJavaElementBindings.get(element);
				bindingastnodes.addAll(astnodes);
				iJavaElementBindings.put(element, bindingastnodes);
				nonfeaturetextMapping.put(asssociatedStringLowwercase, iJavaElementBindings);
			}else{
				Set<ASTNode> bindingastnodes = new HashSet<ASTNode>();
				bindingastnodes.addAll(astnodes);
				iJavaElementBindings.put(element, bindingastnodes);
				nonfeaturetextMapping.put(asssociatedStringLowwercase, iJavaElementBindings);
			}
		}else{
			Map<IJavaElement,Set<ASTNode>> iJavaElementBindings = new HashMap<IJavaElement,Set<ASTNode>>();
			Set<ASTNode> bindingastnodes = new HashSet<ASTNode>();
			bindingastnodes.addAll(astnodes);
			iJavaElementBindings.put(element, bindingastnodes);
			nonfeaturetextMapping.put(asssociatedStringLowwercase, iJavaElementBindings);
		}
	}
	
	/*
	 * Insert a dict to dictionary
	 */
	public void mergeAndOptimizeDict(){
		/*
		assert(project!=null);
		IPath path = project.getLocation();
		path = path.append("/"+tempfilePath);
		tempfilePath = path.toOSString();
		convertToList();
		StringListToFile strToFile = new StringListToFile(allStringList,tempfilePath);
		strToFile.writeToFile();
		*/
		
		
	}
	public void buildBaseVectorSpace(){
		
	}


	public void setProject(IProject selectProject) {
		// TODO Auto-generated method stub
		this.project = selectProject;
	}
	
	public void convertToList() {
		// TODO Auto-generated method stub
		if(!featureNameDictionary.isEmpty())
			allStringList.addAll(featureNameDictionary.keySet());
		if(!nonfeaturetextMapping.isEmpty())
			allStringList.addAll(nonfeaturetextMapping.keySet());
	}
	
	public String replaceSymbolToSpace(String str){
		String resultstr = str;
		for(int i = 0;i < str.length();i++){
			char c = str.charAt(i);
			if(c<='z'&&c>='a'){
				continue;
			}else if(c==' '||c=='\t'){
				continue;
			}else if(resultstr.indexOf(c)!=-1){
				resultstr = resultstr.replace(c,' ');
			}
		}
		
		return resultstr;
	}
}
