// Table.jsx
import React from "react";
import "./Table.css"; // common styles

const Table = ({ data }) => {
  if (!data || data.length === 0) return <p>No data available</p>;

  // Extract headers from first row
  const headers = Object.keys(data[0]);

  const getCellClass = (key, value) => {
    if (key.toLowerCase() === "quantity") {
      if (value === 0) return "stock-out";
      if (value <= 10) return "low-stock";
    }
    return "";
  };

  return (
    <div className="table-wrapper">
      <table>
        <thead>
          <tr>
            {headers.map((h) => (
              <th key={h}>{h.toUpperCase()}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((row, idx) => (
            <tr key={idx}>
              {headers.map((h) => {
                const cellValue = row[h];

                return (
                  <td key={h} className={getCellClass(h, cellValue)}>
                    {/* Render React elements directly, fallback to string/number */}
                    {React.isValidElement(cellValue)
                      ? cellValue
                      : typeof cellValue === "object"
                        ? JSON.stringify(cellValue)
                        : cellValue}
                  </td>
                );
              })}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Table;
