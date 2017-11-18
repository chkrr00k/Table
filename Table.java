package chkrr00k.gui;
//"proudly" made by chkrr00k
// LGPL licence

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class Table extends JPanel implements Iterable<Object[]>{

	private static final long serialVersionUID = 1L;

	private JTable tab;
	private DefaultTableModel dtm;
	
	// constructor
	public Table(int row, int columns) {
		super(new BorderLayout());
		this.tab = new JTable();
		this.dtm = new DefaultTableModel(row, columns);
		tab.setModel(dtm);
		this.tab.setPreferredScrollableViewportSize(new Dimension(300, 100));
		super.add(new JScrollPane(this.tab, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);		
	}
	public Table(){
		this(0, 0);
	}
	
	// adding things
	public void addRow(Object... input){
		dtm.addRow(input);
	}
	public void addHeader(Object... input){
		dtm.setColumnIdentifiers(input);
	}
	public void setAllignment(int allign, int column){
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(allign);
		this.tab.getColumnModel().getColumn(column).setCellRenderer(rightRenderer);
	}
	
	// getting things
	public int getRowCount(){
		return this.tab.getRowCount();
	}
	public int getColumnCount(){
		return this.tab.getColumnCount();
	}
	
	public int getSelectedRow(){
		return this.tab.getSelectedRow();
	}
	public int getSelectedColumn() {
		return this.tab.getSelectedColumn();
	}

	public Object getValueAt(int selectedRow, int selectedColumn) {
		return this.tab.getValueAt(selectedRow, selectedColumn);
	}
	
	public Object[] getRow(int row){
		Object[] result = new Object[this.getColumnCount()];
		for(int i = 0; i < this.getColumnCount(); i++){
			result[i] = this.getValueAt(row, i);
		}
		return result;
	}
	public Object[] getColumn(int column){
		Object[] result = new Object[this.getRowCount()];
		for(int i = 0; i < this.getRowCount(); i++){
			result[i] = this.getValueAt(i, column);
		}
		return result;
	}

	// removing things
	public void removeRow(int row){
		this.dtm.removeRow(row);
	}
	public void clean(){
		int rows = this.getRowCount();
		for(int i = 0; i < rows; i++){
			this.removeRow(0);
		}
	}
	
	// layout things
	public void setRowHeight(int rowHeight){
		this.tab.setRowHeight(rowHeight);
	}
	public void setGridColor(Color gridColor){
		this.tab.setGridColor(gridColor);
	}
	
	// misc
	public void setColumnSelectionAllowed(boolean columnSelectionAllowed){
		this.tab.setColumnSelectionAllowed(columnSelectionAllowed);
	}
	public void setRowSelectionAllowed(boolean rowSelectionAllowed){
		this.tab.setRowSelectionAllowed(rowSelectionAllowed);
	}
	public void setReorderingAllowed(boolean b){
		this.tab.getTableHeader().setReorderingAllowed(b);
	}
	public void addEventListener(TableModelListener ev){
		this.tab.getModel().addTableModelListener(ev);
	}
	public void setEditable(boolean ed){
		this.tab.setEnabled(ed);
	}
	
	public Stream<Object[]> stream(){
		return StreamSupport.stream(Spliterators.spliterator(this.iterator(), this.getRowCount(), Spliterator.SIZED), false);
	}
	@Override
	public Iterator<Object[]> iterator() {
		return new TableIterator(this);
	}
	
	public class TableIterator implements Iterator<Object[]>{

		private Table tab;
		private int currentlyAt;
		
		public TableIterator(Table tab) {
			this.tab = tab;
			this.currentlyAt = 0;
		}

		@Override
		public boolean hasNext() {
			return this.currentlyAt < this.tab.getRowCount();
		}

		@Override
		public Object[] next() {
			return this.tab.getRow(this.currentlyAt++);
		}
		
	}


	
	
}
