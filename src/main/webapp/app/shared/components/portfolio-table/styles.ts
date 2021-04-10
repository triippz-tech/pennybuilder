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

export const styles = {
  table: {
    style: {
      color: "#FFFFFF",
      backgroundColor: "#303030",
    },
  },
  tableWrapper: {
    style: {
      display: 'table',
    },
  },
  header: {
    style: {
      fontSize: '22px',
      color: "#FFFFFF",
      backgroundColor: "#303030",
      minHeight: '56px',
      paddingLeft: '16px',
      paddingRight: '8px',
    },
  },
  subHeader: {
    style: {
      backgroundColor: "#303030",
      minHeight: '52px',
    },
  },
  head: {
    style: {},
  },
  headRow: {
    style: {
      backgroundColor: "#303030",
      minHeight: '56px',
      borderBottomWidth: '1px',
      borderBottomColor: "#564ab1",
      borderBottomStyle: 'solid',
    },
    denseStyle: {
      minHeight: '32px',
    },
  },
  headCells: {
    style: {
      fontSize: '12px',
      fontWeight: 500,
      color: "#FFFFFF",
      paddingLeft: '16px',
      paddingRight: '16px',
    },
    activeSortStyle: {
      color: "#FFFFFF",
      '&:focus': {
        outline: 'none',
      },
      '&:hover:not(:focus)': {
        color: 'rgba(0, 0, 0, .54)',
      },
    },
    inactiveSortStyle: {
      '&:focus': {
        outline: 'none',
        color: 'rgba(0, 0, 0, .54)',
      },
      '&:hover': {
        color: 'rgba(0, 0, 0, .54)',
      },
    },
  },
  contextMenu: {
    style: {
      backgroundColor: '#E91E63',
      fontSize: '18px',
      fontWeight: 400,
      color: '#FFFFFF',
      paddingLeft: '16px',
      paddingRight: '8px',
      transform: 'translate3d(0, -100%, 0)',
      transitionDuration: '125ms',
      transitionTimingFunction: 'cubic-bezier(0, 0, 0.2, 1)',
      willChange: 'transform',
    },
    activeStyle: {
      transform: 'translate3d(0, 0, 0)',
    },
  },
  cells: {
    style: {
      paddingLeft: '16px',
      paddingRight: '16px',
      wordBreak: 'break-word',
    },
  },
  rows: {
    style: {
      fontSize: '13px',
      color: "#FFFFFF",
      backgroundColor: "#303030",
      minHeight: '48px',
      '&:not(:last-of-type)': {
        borderBottomStyle: 'solid',
        borderBottomWidth: '1px',
        borderBottomColor: "#564ab1",
      },
    },
    denseStyle: {
      minHeight: '32px',
    },
    selectedHighlightStyle: {
      // use nth-of-type(n) to override other nth selectors
      '&:nth-of-type(n)': {
        color: '#FFFFFF',
        backgroundColor: 'rgba(0, 0, 0, .7)',
        borderBottomColor: "#303030",
      },
    },
    highlightOnHoverStyle: {
      color: '#FFFFFF',
      backgroundColor: 'rgba(0, 0, 0, .7)',
      transitionDuration: '0.15s',
      transitionProperty: 'background-color',
      borderBottomColor: "#303030",
      outlineStyle: 'solid',
      outlineWidth: '1px',
      outlineColor: "#303030",
    },
    stripedStyle: {
      color: '#FFFFFF',
      backgroundColor: 'rgba(0, 0, 0, .87)',
    },
  },
  expanderRow: {
    style: {
      color: "#FFFFFF",
      backgroundColor: "#303030",
    },
  },
  expanderCell: {
    style: {
      flex: '0 0 48px',
    },
  },
  expanderButton: {
    style: {
      color: '#FFFFFF',
      fill: '#FFFFFF',
      backgroundColor: 'transparent',
      borderRadius: '2px',
      transition: '0.25s',
      height: '100%',
      width: '100%',
      '&:hover:enabled': {
        cursor: 'pointer',
      },
      '&:disabled': {
        color: 'rgba(255, 255, 255, .18)',
      },
      '&:hover:not(:disabled)': {
        cursor: 'pointer',
        backgroundColor: 'rgba(255, 255, 255, .12)',
      },
      '&:focus': {
        outline: 'none',
        backgroundColor: 'rgba(255, 255, 255, .54)',
      },
      svg: {
        margin: 'auto',
      },
    },
  },
  pagination: {
    style: {
      color: 'rgba(0, 0, 0, 0.54)',
      fontSize: '13px',
      minHeight: '56px',
      backgroundColor: "#303030",
      borderTopStyle: 'solid',
      borderTopWidth: '1px',
      borderTopColor: "#564ab1",
    },
    pageButtonsStyle: {
      borderRadius: '50%',
      height: '40px',
      width: '40px',
      padding: '8px',
      margin: 'px',
      cursor: 'pointer',
      transition: '0.4s',
      color: '#FFFFFF',
      fill: '#FFFFFF',
      backgroundColor: 'transparent',
      '&:disabled': {
        cursor: 'unset',
        color: 'rgba(255, 255, 255, .18)',
        fill: 'rgba(255, 255, 255, .18)',
      },
      '&:hover:not(:disabled)': {
        backgroundColor: 'rgba(255, 255, 255, .12)',
      },
      '&:focus': {
        outline: 'none',
        backgroundColor: 'rgba(255, 255, 255, .54)',
      },
    },
  },
  noData: {
    style: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      color: "#FFFFFF",
      backgroundColor: "#303030",
    },
  },
  progress: {
    style: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      color: "#FFFFFF",
      backgroundColor: "#303030",
    },
  },
};

