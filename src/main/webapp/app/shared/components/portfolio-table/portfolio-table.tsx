/*
 *     PennyBuilder
 *     Copyright (C) 2021  Mark Tripoli, RamChandraReddy Manda
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import React, {useState} from "react";
import DataTable from "react-data-table-component";
import {PositionQuote} from "app/shared/model/iex/quote";
import {Row, Col, Button} from "reactstrap";
import PortfolioTableExpanded from "app/shared/components/portfolio-table/portfolio-table-expanded";
import {styles} from "./styles";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import NumberFormat, {NumberFormatValues} from "react-number-format";
import {thousandsSeparator} from "app/shared/util/number-utils";
import bigDecimal from "js-big-decimal";


export interface PortfolioTableProps {
  positions: Array<PositionQuote>;
  onSelectedRowsChanged: (selectedRowState) => void;
  onUpdateRow: (position: PositionQuote, positionQuantity: number) => void;
}

export const PortfolioTable: React.FC<PortfolioTableProps> = (props) => {
  const [selectedRowId, setSelectedRowId] = useState<number>(null);
  const [positionQuantity, setPositionQuantity] = useState<number>(0);

  const onChange = (value: NumberFormatValues) => setPositionQuantity(value.floatValue);

  const onUpdate = (row, quantity) => {
    props.onUpdateRow(row, quantity);
    setSelectedRowId(null);
  }


  const columns = [
    {
      name: 'Symbol',
      selector: 'symbol',
      sortable: true,
    },
    {
      name: 'Shares',
      selector: 'quantity',
      sortable: true,
      /* eslint object-shorthand: 0 */
      cell: row => {
        if (selectedRowId === null || selectedRowId !== row.id) return row.quantity
        return (
          <div
            key={row.id}
            className="input-group"
            onKeyPress={e => {
              if (e.key === "Enter") {
                onUpdate(row, positionQuantity);
              }
            }}>
            <NumberFormat
              autoFocus
              className="form-control"
              allowNegative={false}
              decimalSeparator={'.'}
              decimalScale={8}
              defaultValue={positionQuantity}
              placeholder="Enter # of Shares"
              onValueChange={onChange}
            />
            <div className="input-group-append">
              <Button
                size='sm'
                color="success"
                onClick={e => onUpdate(row, positionQuantity)}
              >
                <FontAwesomeIcon icon={"check"}/>
              </Button>
            </div>
          </div>
        );
      }
    },
    {
      name: 'Total',
      selector: 'totalValue',
      sortable: true,
      cell: row => `$ ${thousandsSeparator(bigDecimal.round(row.totalValue, 2))}`
    },
    {
      name: 'Last Price',
      selector: 'iexRealtimePrice',
      sortable: true,
      cell: row => `$ ${thousandsSeparator(row.iexRealtimePrice )}`
    },
    {
      name: 'Change',
      selector: 'change',
      sortable: true,
      cell: row => <div
        className={`text-${row.change > 0 ? "success" : "danger"}`}>{row.change > 0 && `+`}{row.change}</div>
    },
    {
      name: 'CHG %',
      selector: 'changePercent',
      sortable: true,
      cell: row => <div className={`text-${row.changePercent > 0 ? "success" : "danger"}`}>{row.changePercent}</div>
    },
    {
      name: 'Volume',
      selector: 'volume',
      sortable: true,
      cell: row => `$ ${thousandsSeparator(row.volume )}`
    },
    {
      name: '52W Range',
      selector: 'week52Low',
      sortable: true,
      cell: row => `$ ${thousandsSeparator(row.week52Low )}`
    },
    {
      name: '52W High',
      selector: 'week52High',
      sortable: true,
      cell: row => `$ ${thousandsSeparator(row.week52High )}`
    },
    {
      name: 'Market Cap',
      selector: 'marketCap',
      sortable: true,
      cell: row => `$ ${thousandsSeparator(row.marketCap )}`
    },
  ]

  return <DataTable
    columns={columns}
    data={props.positions}
    customStyles={styles}
    expandableRows
    highlightOnHover
    defaultSortField="name"
    selectableRows
    onSelectedRowsChange={props.onSelectedRowsChanged}
    expandableRowsComponent={<PortfolioTableExpanded data={null}/>}
    onRowDoubleClicked={(row: PositionQuote, e: MouseEvent) => {
      setSelectedRowId(row.id);
      setPositionQuantity(row.quantity);
    }}
  />
};

export default PortfolioTable;
