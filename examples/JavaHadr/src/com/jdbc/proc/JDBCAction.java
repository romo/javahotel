/*
 * Copyright 2012 stanislawbartkowski@gmail.com 
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
package com.jdbc.proc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IJDBCAction
 * 
 * @author sbartkowski
 * 
 */
class JDBCAction implements IJDBCAction {

	private final IGetConnection getCon;
	private final String INSERT = "INSERT INTO PERSONS VALUES(DEFAULT,?,?)";
	private final String QUERY = "SELECT * FROM PERSONS";
	private final String CREATESQL = "CREATE TABLE PERSONS (ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY, NAME VARCHAR(100), FAMILYNAME VARCHAR(100))";
	private final String DROPSQL = "DROP TABLE PERSONS";

	private SQLCommandRunTime context = new SQLCommandRunTime();

	/**
	 * Constructor
	 * 
	 * @param getCon
	 *            Connection provider
	 */
	JDBCAction(IGetConnection getCon) {
		this.getCon = getCon;
	}

	@Override
	public void connect() {
		if (context.con != null) {
			context.lastResult = "Already connected.";
			return;
		}
		try {
			context.con = getCon.getCon();
			context.lastResult = "Ok, connected.";
		} catch (SQLException e) {
			e.printStackTrace();
			context.lastResult = e.getMessage();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			context.lastResult = e.getMessage();
		}
	}

	@Override
	public void disconnect() {
		SqlCommand command = new SqlCommand(context) {

			@Override
			protected void command() throws SQLException {
				context.con.close();
				context.con = null;
			}
		};
		command.runCommand();
	}

	/**
	 * Add person command class implementing SqlCommand abstract class
	 * 
	 * @author sbartkowski
	 * 
	 */
	private class AddPersonCommand extends SqlCommand {

		private final PersonRecord p;

		AddPersonCommand(SQLCommandRunTime context, PersonRecord p) {
			super(context);
			this.p = p;
		}

		@Override
		protected void command() throws SQLException {
			PreparedStatement stmt = context.con.prepareStatement(INSERT);
			stmt.setString(1, p.getName());
			stmt.setString(2, p.getFamilyName());
			stmt.execute();
			stmt.close();
		}
	}

	/**
	 * Add person command
	 */
	@Override
	public void addPerson(PersonRecord re) {
		AddPersonCommand command = new AddPersonCommand(context, re);
		command.runCommand();
	}

	/**
	 * Enumerate all persons persisted in the database
	 */
	@Override
	public List<PersonRecord> getList() {
		final List<PersonRecord> pList = new ArrayList<PersonRecord>();
		SqlCommand command = new SqlCommand(context) {

			@Override
			protected void command() throws SQLException {
				ResultSet res = context.con.createStatement().executeQuery(
						QUERY);
				while (res.next()) {
					int id = res.getInt(1);
					String name = res.getString(2);
					String familyName = res.getString(3);
					pList.add(new PersonRecord(id, name, familyName));
				}

			}

		};
		command.runCommand();
		return pList;
	}

	/**
	 * Utility class executing single update SQL command
	 * 
	 * @author sbartkowski
	 * 
	 */
	private class RunUpdateCommand extends SqlCommand {

		private final String sqlCommand;

		RunUpdateCommand(String sqlCommand, SQLCommandRunTime context) {
			super(context);
			this.sqlCommand = sqlCommand;
		}

		@Override
		protected void command() throws SQLException {
			Statement sqlS = context.con.createStatement();
			sqlS.execute(sqlCommand);
		}

	}

	@Override
	public String getStatus() {
		if (context.con != null) {
			String autoC;
			try {
				if (context.con.getAutoCommit()) {
					autoC = "autocommit on";
				} else {
					autoC = "autocommit off";
				}
			} catch (SQLException e) {
				// rather unlikely in this context
				autoC = e.getMessage();
			}
			return "Connected. (" + autoC + ")";
		} else {
			return "Not connected";
		}
	}

	@Override
	public String getLastResult() {
		return context.lastResult;
	}

	/**
	 * Create table command
	 */
	@Override
	public void createTable() {
		new RunUpdateCommand(CREATESQL, context).runCommand();
	}

	/**
	 * Drop table command
	 */
	@Override
	public void dropTable() {
		new RunUpdateCommand(DROPSQL, context).runCommand();
	}

	/**
	 * Autocommit command
	 */
	@Override
	public void setAutocommit(final boolean on) {
		SqlCommand command = new SqlCommand(context) {

			@Override
			protected void command() throws SQLException {
				context.con.setAutoCommit(on);

			}

		};
		command.runCommand();
	}
}