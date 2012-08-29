package com.catalystitservices.vaadin;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings( "serial" )
public class TestApp extends Application {
	@Override
	public void init() {
		Window mainWindow = new TestWindow();
		setMainWindow( mainWindow );
	}

	public class TestWindow extends Window {
		public TestWindow() {
			IndexedContainer container = createContainer();
			Table table = createTable( container );

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponent( table );
			mainLayout.setSizeFull();

			setContent( mainLayout );
		}

		/*
		 * Build a model for the table with the data : Lots of ways of doing
		 * this; Just a quick-and-dirty example
		 */
		private IndexedContainer createContainer() {
			IndexedContainer container = new IndexedContainer();
			container.addContainerProperty( "name", String.class, null );
			container.addContainerProperty( "number", Integer.class, null );

			addItem( container, "Bob", 10 );
			addItem( container, "Harry", 1 );
			addItem( container, "Margaret", 0 );
			addItem( container, "Glenda", 22 );
			addItem( container, "Boris", 0 );
			addItem( container, "Jessica", 24 );
			return container;
		}

		private void addItem( IndexedContainer container, String name,
				int number ) {
			Object itemId = container.addItem();
			container.getItem( itemId ).getItemProperty( "name" )
					.setValue( name );
			container.getItem( itemId ).getItemProperty( "number" )
					.setValue( number );
		}

		private Table createTable( IndexedContainer container ) {
			Table table = new Table() {
				@Override
				protected String formatPropertyValue( Object rowId,
						Object colId, Property property ) {

					Object value = property.getValue();

					if ( value instanceof Integer && ( (Integer) value ) == 0 ) {
						return "";
					}

					return super.formatPropertyValue( rowId, colId, property );
				}
			};
			table.setContainerDataSource( container );
			table.setSizeFull();
			table.setVisibleColumns( new Object[] { "name", "number" } );
			table.setColumnExpandRatio( "number", 1f );
			return table;
		}

	}
}