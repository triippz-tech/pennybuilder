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

import React from "react";
import AsyncSelect from 'react-select/async';
import axios from "axios";
import "./styles.css";
import {PositionQuote} from "app/shared/model/iex/quote";


export interface SymbolSearchProps {
  onSelect: (asset: PositionQuote) => void;
}

export const SymbolSearch: React.FC<SymbolSearchProps> = (props) => {

  const CustomOption = ({label, name, asset}) => {
    return (
      <div className="pl-4">
        <div className="row">
          <strong>{label}</strong>
        </div>
        <div className="row">
          {asset.securityType} | {asset.region} | {asset.exchange}
        </div>
      </div>
    );
  }

  return (
    <AsyncSelect
      placeholder="GME, TSLA, AMC.."
      className='colorful-select dropdown-primary mx-2 text-dark'
      formatOptionLabel={CustomOption}
      onChange={(obj) => props.onSelect(obj.asset)}
      cacheOptions
      defaultOptions
      menuColor='red'
      loadOptions={(query) => {
        return new Promise((resolve, reject) => {
          axios.get(`api/assets/search?symbol.contains=${query}`)
            .then((stocks) => {
              resolve(stocks.data.map((value) => ({
                label: value.symbol,
                value: value.symbol,
                asset: value
              })))
            })
            .catch(reject);
        });
      }}
      styles={{
        control: (provided, state) => ({
          ...provided,
          background: '#fff',
          borderColor: '#9e9e9e',
          minHeight: '70px',
          height: '70px',
          boxShadow: state.isFocused ? null : null,
          marginLeft: '10px'
        }),
        valueContainer: (provided, state) => ({
          ...provided,
          height: '70px',
          padding: '0px 6px 0px 20px',
          width: '100%'
        }),

        input: (provided, state) => ({
          ...provided,
          margin: '0px',
        }),
        indicatorSeparator: state => ({
          display: 'none',
        }),
        indicatorsContainer: (provided, state) => ({
          ...provided,
          height: '70px',
        }),
        container: base => ({
          ...base,
          width: '400px',
        }),
        menuPortal: base => ({...base, zIndex: 9999}),
        menu: provided => ({...provided, zIndex: "9999 !important"}),
        option: styles => ({ ...styles, height: '50px'}),
      }}
    />

  )
}

export default SymbolSearch;
