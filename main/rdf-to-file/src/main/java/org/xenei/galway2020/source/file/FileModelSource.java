package org.xenei.galway2020.source.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.WrappedIterator;
import org.apache.log4j.Logger;
import org.xenei.galway2020.ModelSource;

/**
 * Reads files in a directory and sub directories that have recognized file extensions.
 * 
 * Will not process files created after the instance is constructed.
 *
 */
public class FileModelSource implements ModelSource {

	private static final String FILE_DIR = "dir";

	private final File dir;

	private final NotFileFilter notDotFilter = new NotFileFilter( new PrefixFileFilter( "." ));
	private final boolean deleteAfterRead;
	private final Logger LOG = Logger.getLogger(FileModelSource.class);
	private final AgeFileFilter dateLimit;
	
	/**
	 * Constructor.
	 * 
	 * <ul>
	 * <li>dir - The directory to scan</li>
	 * <li>deleteAfterRead - delete the file after reading</li>
	 * </ul>
	 * 
	 * @param cfg The configuration.
	 */
	public FileModelSource(Configuration cfg) {
		if (StringUtils.isBlank( cfg.getString(FILE_DIR)))
		{
			throw new IllegalArgumentException( "Configuration option "+FILE_DIR+" must be specified");
		}
		dir = new File(cfg.getString(FILE_DIR));
		if (!dir.exists())
		{
			throw new IllegalArgumentException( String.format( 
					"Configuration option %s (%s)  must exist", FILE_DIR, dir));
		}
		if (!dir.isDirectory())
		{
			throw new IllegalArgumentException( String.format( 
					"Configuration option %s (%s)  must be a directory", FILE_DIR, dir));
		}
		dateLimit = new AgeFileFilter(System.currentTimeMillis(),true);
		deleteAfterRead = cfg.getBoolean( "deleteAfterRead", false );
		
	}

	@Override
	public ExtendedIterator<Model> modelIterator() {
		
		Collection<File> col = FileUtils.listFiles( dir, new AndFileFilter( dateLimit, notDotFilter), notDotFilter);
		return WrappedIterator.create(col.iterator()).filterKeep( new ModelFilter() )
		.mapWith( new FileToModel() );
	}
	
	private static class ModelFilter implements Predicate<File> {

		@Override
		public boolean test(File t) {
			return RDFLanguages.filenameToLang(t.getName())!=null;
		}
		
	}
	
	private class FileToModel implements Function<File,Model> {

		@Override
		public Model apply(File t) {
			Model retval = ModelFactory.createDefaultModel();
			Lang lang = RDFLanguages.filenameToLang(t.getName());
			try {
				LOG.info( "Reading "+t.getName() );
				retval.read( new FileInputStream( t ), t.getName(), lang.getName());
				if (deleteAfterRead)
				{
					try {
						if (!t.delete())
						{
							LOG.warn( String.format( "Can not delete %s", t.getName()));
						}
					}
					catch (SecurityException e)
					{
						LOG.warn( String.format( "Can not delete %s", t.getName()), e );
					}
				}
			} catch (FileNotFoundException e)
			{
				LOG.error( String.format( "File %s went missing or could not be read.", t.getName()), e );
			}
			catch (SecurityException e)
			{
				LOG.warn( String.format( "Can not read %s", t.getName()), e );
			}
			
			return retval;
			
		}
		
	}
}
