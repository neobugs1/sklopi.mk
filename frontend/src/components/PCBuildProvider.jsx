import React, { createContext, useContext, useState, useEffect } from "react";
import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";

const PCBuildContext = createContext();

export const PCBuildProvider = ({ children }) => {
  const [pcBuild, setPcBuild] = useState({
    id: null,
    cpu: null,
    cpuCooler: null,
    motherboard: null,
    memory: [],
    storage: [],
    gpu: null,
    psu: null,
    case: null,
  });

  const navigate = useNavigate();

  useEffect(() => {
    const loadOrCreatePC = async () => {
      try {
        // Check if the PC ID is stored in cookies
        let pcId = Cookies.get("pcId");

        if (!pcId) {
          // If no PC ID in cookies, create a new PC
          const createResponse = await fetch("http://localhost:8080/api/pcs", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({}),
          });

          if (!createResponse.ok) {
            throw new Error("Failed to create a new PC");
          }

          const newPC = await createResponse.json();
          pcId = newPC.id;

          // Store the new PC ID in cookies
          Cookies.set("pcId", pcId);
        }

        // Load the PC from the backend using the stored or newly created ID
        const response = await fetch(`http://localhost:8080/api/pcs/${pcId}`);
        if (!response.ok) {
          throw new Error("Failed to load PC");
        }

        const pcData = await response.json();
        setPcBuild(pcData);
      } catch (error) {
        console.error(error);
      }
    };

    loadOrCreatePC();
  }, []);

  const addComponent = async (productId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/pcs/${pcBuild.id}/add-component`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ productId }), // Send only the product ID
      });

      if (!response.ok) {
        throw new Error("Failed to add component");
      }

      const updatedPC = await response.json();
      setPcBuild(updatedPC);
      navigate("/builder");
    } catch (error) {
      console.error(error);
    }
  };

  return <PCBuildContext.Provider value={{ pcBuild, addComponent }}>{children}</PCBuildContext.Provider>;
};

export const usePCBuild = () => useContext(PCBuildContext);
