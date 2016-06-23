package loongplugin.recommendation.typesystem.typing.jdt.model;

import java.util.Collection;
import java.util.Set;

import loongplugin.color.coloredfile.CLRAnnotatedSourceFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;


/**
 * typing providers are created on a per project basis. it calculates all the
 * checks that need to be performed by CIDE. it informs CIDE when new checks are
 * necessary and CIDE informs it when files need to be rechecked.
 * 
 * types that cannot be resolved (e.g. a method invocation that has no target in
 * any variant) are not handled here but reported directly by the language.
 * CIDE's typing only considers additional checks on top of existing typing of
 * that language.
 * 
 * @author ckaestne
 * 
 */
public interface ITypingProvider {

	IProject getProject();

	/**
	 * gets all concrete typing checks (every pair of call and references
	 * including evaluation strategy/rules and error message)
	 * 
	 * checks are only available after a first update call
	 * 
	 * @return
	 */
	Set<ITypingCheck> getChecks();

	/**
	 * listner to inform about new and obsolete typing checks (batched). a
	 * change in the current checks should invoke CIDE to evaluate the new
	 * conditions or remove obsolete error messages
	 * 
	 * @param listener
	 */
	void addTypingCheckListener(ITypingCheckListener listener);

	void removeTypingCheckListener(ITypingCheckListener listener);

	/**
	 * informs the typing provider that the content of one or more files has
	 * changed. this operation is blocking and should be run from a thread/job
	 * @param monitor TODO
	 */
	void updateFile(Collection<CLRAnnotatedSourceFile> files, IProgressMonitor monitor);

	/**
	 * the entire typechecking for the project should be redone. this operation
	 * is blocking and should be run from a thread/job
	 * @param monitor TODO
	 */
	void updateAll(IProgressMonitor monitor);

	/**
	 * called before the checks of one or more files are reevaluated. usually a
	 * typing provider does not need to react. however, in some cases when the
	 * evaluation of checks relies on cached values in the typing provider, this
	 * gives the chance to update those caches.
	 * 
	 * blocking operation, should be run from a thread/schedule before
	 * evaluation. no not perform asynchronous operations when implementing this
	 * method, if the reevaluation depends on the results.
	 * 
	 * @param files
	 *            files that are to be reevaluated
	 * @param monitor TODO
	 */
	void prepareReevaluation(Collection<CLRAnnotatedSourceFile> files, IProgressMonitor monitor);

	/**
	 * same as prepareReevaluation(files) but for the entire project
	 * @param monitor TODO
	 * 
	 */
	void prepareReevaluationAll(IProgressMonitor monitor);

}
