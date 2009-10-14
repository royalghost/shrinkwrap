/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.shrinkwrap.impl.base.test;

import java.io.File;
import java.net.URL;

import junit.framework.Assert;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Asset;
import org.jboss.shrinkwrap.api.Path;
import org.jboss.shrinkwrap.api.container.ClassContainer;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.api.container.ManifestContainer;
import org.jboss.shrinkwrap.api.container.ResourceContainer;
import org.jboss.shrinkwrap.impl.base.asset.AssetUtil;
import org.jboss.shrinkwrap.impl.base.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.impl.base.path.BasicPath;
import org.jboss.shrinkwrap.impl.base.spec.donotchange.DummyClassUsedForClassResourceTest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * DynamicContainerTestBase
 *
 * @author <a href="mailto:aslak@conduct.no">Aslak Knutsen</a>
 * @version $Revision: $
 * @param <T>
 */
@RunWith(ContainerTestRunner.class)
public abstract class DynamicContainerTestBase<T extends Archive<T>> extends ArchiveTestBase<T>
{
   
   protected abstract Path getResourcePath();
   protected abstract ResourceContainer<T> getResourceContainer();
   protected abstract Path getClassPath();
   protected abstract ClassContainer<T> getClassContainer();
   protected abstract Path getManifestPath();
   protected abstract ManifestContainer<T> getManifestContainer();
   protected abstract Path getLibraryPath();
   protected abstract LibraryContainer<T> getLibraryContainer();
   
   protected URL getURLForClassResource(String name) {
      return Thread.currentThread().getContextClassLoader().getResource(name);
   }
   
   protected File getFileForClassResource(String name) throws Exception {
      return new File(getURLForClassResource(name).toURI());
   }
   
   protected Asset getAssetForClassResource(String name) {
      return new ClassLoaderAsset(name);
   }
   
   //-------------------------------------------------------------------------------------||
   // Test Implementations - ManifestContainer -------------------------------------------||
   //-------------------------------------------------------------------------------------||

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testSetManifestResource() throws Exception {
      getManifestContainer().setManifest(NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getManifestPath(), "MANIFEST.FM");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(ManifestContainer.class)
   public void testSetManifestFile() throws Exception {
      getManifestContainer().setManifest(getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "MANIFEST.FM");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testSetManifestURL() throws Exception {
      getManifestContainer().setManifest(getURLForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "MANIFEST.FM");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testSetManifestAsset() throws Exception {
      getManifestContainer().setManifest(getAssetForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "MANIFEST.FM");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestResource() throws Exception {
      getManifestContainer().addManifestResource(NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getManifestPath(), NAME_TEST_PROPERTIES);
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestFile() throws Exception {
      getManifestContainer().addManifestResource(getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "Test.properties");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestURL() throws Exception {
      Path targetPath = new BasicPath("Test.properties");
      getManifestContainer().addManifestResource(targetPath, getURLForClassResource(NAME_TEST_PROPERTIES));
      Path testPath = new BasicPath(getManifestPath(), targetPath);
      Assert.assertTrue("Archive should contain " + testPath, getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestStringTargetResource() throws Exception {
      getManifestContainer().addManifestResource("Test.txt", NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getManifestPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestStringTargetFile() throws Exception {
      getManifestContainer().addManifestResource("Test.txt", getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestStringTargetURL() throws Exception {
      getManifestContainer().addManifestResource("Test.txt", getURLForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestStringTargetAsset() throws Exception {
      getManifestContainer().addManifestResource("Test.txt", getAssetForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestPathTargetResource() throws Exception {
      getManifestContainer().addManifestResource(new BasicPath("Test.txt"), NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getManifestPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestPathTargetFile() throws Exception {
      getManifestContainer().addManifestResource(new BasicPath("Test.txt"), getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestPathTargetURL() throws Exception {
      getManifestContainer().addManifestResource(new BasicPath("Test.txt"), getURLForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ManifestContainer.class)
   public void testAddManifestPathTargetAsset() throws Exception {
      getManifestContainer().addManifestResource(new BasicPath("Test.txt"), getAssetForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getManifestPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   //-------------------------------------------------------------------------------------||
   // Test Implementations - ResourceContainer -------------------------------------------||
   //-------------------------------------------------------------------------------------||

   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourceResource() throws Exception {
      getResourceContainer().addResource(NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getResourcePath(), NAME_TEST_PROPERTIES);
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourceFile() throws Exception {
      getResourceContainer().addResource(getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getResourcePath(), "Test.properties");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourceURL() throws Exception {
      Path targetPath = new BasicPath("Test.properties");
      getResourceContainer().addResource(targetPath, getURLForClassResource(NAME_TEST_PROPERTIES));
      Path testPath = new BasicPath(getResourcePath(), targetPath);
      Assert.assertTrue("Archive should contain " + testPath, getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourceStringTargetResource() throws Exception {
      getResourceContainer().addResource("Test.txt", NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getResourcePath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourceStringTargetFile() throws Exception {
      getResourceContainer().addResource("Test.txt", getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getResourcePath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourceStringTargetURL() throws Exception {
      
      getResourceContainer().addResource("Test.txt", getURLForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getResourcePath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourceStringTargetAsset() throws Exception {
      getResourceContainer().addResource("Test.txt", getAssetForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getResourcePath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourcePathTargetResource() throws Exception {
      getResourceContainer().addResource(new BasicPath("Test.txt"), NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getResourcePath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourcePathTargetFile() throws Exception {
      getResourceContainer().addResource(new BasicPath("Test.txt"), getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getResourcePath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourcePathTargetURL() throws Exception {
      getResourceContainer().addResource(new BasicPath("Test.txt"), getURLForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getResourcePath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(ResourceContainer.class)
   public void testAddResourcePathTargetAsset() throws Exception {
      getResourceContainer().addResource(new BasicPath("Test.txt"), getAssetForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getResourcePath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   //-------------------------------------------------------------------------------------||
   // Test Implementations - ClassContainer ----------------------------------------------||
   //-------------------------------------------------------------------------------------||

   /**
    * Ensure a class can be added to a container
    * 
    * @throws Exception
    */
   @Test
   @ArchiveType(ClassContainer.class)
   public void testAddClass() throws Exception
   {
      getClassContainer().addClass(DummyClassUsedForClassResourceTest.class);

      Path expectedPath = new BasicPath(getClassPath(), AssetUtil
            .getFullPathForClassResource(DummyClassUsedForClassResourceTest.class));

      Assert.assertTrue(
            "A class should be located at " + expectedPath.get(), 
            getArchive().contains(expectedPath));
   }

   /**
    * Ensure classes can be added to containers
    * 
    * @throws Exception
    */
   @Test
   @ArchiveType(ClassContainer.class)
   public void testAddClasses() throws Exception
   {
      getClassContainer().addClasses(DummyClassUsedForClassResourceTest.class);

      Path expectedPath = new BasicPath(getClassPath(), AssetUtil
            .getFullPathForClassResource(DummyClassUsedForClassResourceTest.class));

      Assert.assertTrue(
            "A class should be located at " + expectedPath.get(), 
            getArchive().contains(expectedPath));
   }

   /**
    * Ensure a package can be added to a container
    * 
    * @throws Exception
    */
   @Test
   @ArchiveType(ClassContainer.class)
   public void testAddPackage() throws Exception
   {
      getClassContainer().addPackage(DummyClassUsedForClassResourceTest.class.getPackage());

      Path expectedPath = new BasicPath(getClassPath(), AssetUtil
            .getFullPathForClassResource(DummyClassUsedForClassResourceTest.class));

      Assert.assertTrue(
            "A class should be located at " + expectedPath.get(), 
            getArchive().contains(expectedPath));
   }

   /**
    * Ensure packages can be added to containers
    * 
    * @throws Exception
    */
   @Test
   @ArchiveType(ClassContainer.class)
   public void testAddPackageNonRecursive() throws Exception
   {
      getClassContainer().addPackages(false, DummyClassUsedForClassResourceTest.class.getPackage());

      Path expectedPath = new BasicPath(getClassPath(), AssetUtil
            .getFullPathForClassResource(DummyClassUsedForClassResourceTest.class));

      Assert.assertTrue(
            "A class should be located at " + expectedPath.get(), 
            getArchive().contains(expectedPath));
   }

   //-------------------------------------------------------------------------------------||
   // Test Implementations - LibraryContainer ----------------------------------------------||
   //-------------------------------------------------------------------------------------||
   
   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryResource() throws Exception {
      getLibraryContainer().addLibrary(NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getLibraryPath(), NAME_TEST_PROPERTIES);
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryFile() throws Exception {
      getLibraryContainer().addLibrary(getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getLibraryPath(), "Test.properties");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryURL() throws Exception {
      final Path targetPath = new BasicPath("Test.properties");
      getLibraryContainer().addLibrary(targetPath, getURLForClassResource(NAME_TEST_PROPERTIES));
      Path testPath = new BasicPath(getLibraryPath(), targetPath);
      Assert.assertTrue("Archive should contain " + testPath, getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryStringTargetResource() throws Exception {
      getLibraryContainer().addLibrary("Test.txt", NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getLibraryPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryStringTargetFile() throws Exception {
      getLibraryContainer().addLibrary("Test.txt", getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getLibraryPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryStringTargetURL() throws Exception {
      getLibraryContainer().addLibrary("Test.txt", getURLForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getLibraryPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryStringTargetAsset() throws Exception {
      getLibraryContainer().addLibrary("Test.txt", getAssetForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getLibraryPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryPathTargetResource() throws Exception {
      getLibraryContainer().addLibrary(new BasicPath("Test.txt"), NAME_TEST_PROPERTIES);
      
      Path testPath = new BasicPath(getLibraryPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryPathTargetFile() throws Exception {
      getLibraryContainer().addLibrary(new BasicPath("Test.txt"), getFileForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getLibraryPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryPathTargetURL() throws Exception {
      getLibraryContainer().addLibrary(new BasicPath("Test.txt"), getURLForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getLibraryPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }

   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryPathTargetAsset() throws Exception {
      getLibraryContainer().addLibrary(new BasicPath("Test.txt"), getAssetForClassResource(NAME_TEST_PROPERTIES));
      
      Path testPath = new BasicPath(getLibraryPath(), "Test.txt");
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }
   
   @Test
   @ArchiveType(LibraryContainer.class)
   public void testAddLibraryArchive() throws Exception {
      Archive<?> archive = createNewArchive();
      getLibraryContainer().addLibrary(archive);
      
      Path testPath = new BasicPath(getLibraryPath(), archive.getName());
      Assert.assertTrue(
            "Archive should contain " + testPath,
            getArchive().contains(testPath));
   }


}